package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.game.stack.StackObject;
import java.util.HashMap;
import java.util.Map;
import mage.constants.WatcherScope;
import mage.watchers.Watcher;
import mage.util.CardUtil;


/**
 *
 * @author @stwalsh4118
 */
public final class DarkApostle extends CardImpl {

    public DarkApostle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Gift of Chaos -- {3}, {T}: The next noncreature spell you cast this turn has cascade.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
            new DarkApostleEffect().setText("The next noncreature spell you cast this turn has cascade."), new ManaCostsImpl("{3}"));
        ability.addCost(new TapSourceCost());
        ability.setAbilityWord(AbilityWord.GIFT_OF_CHAOS);
        this.addAbility(ability, new DarkApostleWatcher());


    }

    private DarkApostle(final DarkApostle card) {
        super(card);
    }

    @Override
    public DarkApostle copy() {
        return new DarkApostle(this);
    }
}

class DarkApostleEffect extends ContinuousEffectImpl {

    private int spellsCast;
    private final Ability cascadeAbility = new CascadeAbility();

    DarkApostleEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "the next noncreature spell you cast this turn has cascade";
    }

    private DarkApostleEffect(final DarkApostleEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        DarkApostleWatcher watcher = game.getState().getWatcher(DarkApostleWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        DarkApostleWatcher watcher = game.getState().getWatcher(DarkApostleWatcher.class);
        System.out.println(watcher);
        if (watcher == null) {
            return false;
        }

        //check if spell was cast before
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }

        //check cast for noncreature spell and add cascade
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    if (!spell.isCreature()) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                        return true;
                    }
                }
            }
        }

        return false;
    }


    @Override
    public DarkApostleEffect copy() {
        return new DarkApostleEffect(this);
    }
}

class DarkApostleWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    DarkApostleWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null && !spell.isCreature(game)) {
            playerMap.compute(event.getPlayerId(), CardUtil::setOrIncrementValue);
        }
    }

    @Override
    public void reset() {
        super.reset();
        playerMap.clear();
    }

    int getCount(UUID playerId) {
        return playerMap.getOrDefault(playerId, 0);
    }
}

