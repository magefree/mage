package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CascadeAbility;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
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
public final class SloppityBilepiper extends CardImpl {

    public SloppityBilepiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Jolly Gutpipes -- {2}, {T}, Sacrifice a creature: The next creature spell you cast this turn has cascade.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new SloppityBilepiperEffect().setText("The next creature spell you cast this turn has cascade."), new ManaCostsImpl("{2}")).withFlavorWord("Jolly Gutpipes");
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_A_CREATURE)));
        this.addAbility(ability, new SloppityBilepiperWatcher());
    }

    private SloppityBilepiper(final SloppityBilepiper card) {
        super(card);
    }

    @Override
    public SloppityBilepiper copy() {
        return new SloppityBilepiper(this);
    }
}

class SloppityBilepiperEffect extends ContinuousEffectImpl {

    private int spellsCast;
    private final Ability cascadeAbility = new CascadeAbility();

    SloppityBilepiperEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
    }

    private SloppityBilepiperEffect(final SloppityBilepiperEffect effect) {
        super(effect);
        this.spellsCast = effect.spellsCast;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        SloppityBilepiperWatcher watcher = game.getState().getWatcher(SloppityBilepiperWatcher.class);
        if (watcher != null) {
            spellsCast = watcher.getCount(source.getControllerId());
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        SloppityBilepiperWatcher watcher = game.getState().getWatcher(SloppityBilepiperWatcher.class);
        System.out.println(watcher);
        if (watcher == null) {
            return false;
        }

        //check if spell was cast before
        if (watcher.getCount(source.getControllerId()) > spellsCast) {
            discard(); // only one use
            return false;
        }

        //check cast for creature spell and add cascade
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (StackObject stackObject : game.getStack()) {
                if ((stackObject instanceof Spell) && !stackObject.isCopy() && stackObject.isControlledBy(source.getControllerId())) {
                    Spell spell = (Spell) stackObject;
                    if (spell.isCreature()) {
                        game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                        return true;
                    }
                }
            }
        }

        return false;
    }


    @Override
    public SloppityBilepiperEffect copy() {
        return new SloppityBilepiperEffect(this);
    }
}

class SloppityBilepiperWatcher extends Watcher {

    private final Map<UUID, Integer> playerMap = new HashMap<>();

    SloppityBilepiperWatcher() {
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
