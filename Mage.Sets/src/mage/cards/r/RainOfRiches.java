package mage.cards.r;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TreasureToken;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;
import mage.watchers.common.ManaPaidSourceWatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class RainOfRiches extends CardImpl {

    public RainOfRiches(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}{R}");

        // When Rain of Riches enters the battlefield, create two Treasure tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2)));

        // The first spell you cast each turn that mana from a Treasure was spent to cast has cascade.
        //      (When you cast the spell, exile cards from the top of your library until you exile a nonland card that costs less.
        //       You may cast it without paying its mana cost.
        //       Put the exiled cards on the bottom of your library in a random order.)
        this.addAbility(
                new SimpleStaticAbility(Zone.BATTLEFIELD, new RainOfRichesGainsCascadeEffect()),
                new RainOfRichesWatcher()
        );
    }

    private RainOfRiches(final RainOfRiches card) {
        super(card);
    }

    @Override
    public RainOfRiches copy() {
        return new RainOfRiches(this);
    }
}

class RainOfRichesGainsCascadeEffect extends ContinuousEffectImpl {

    private final Ability cascadeAbility = new CascadeAbility();

    RainOfRichesGainsCascadeEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText =
                "The first spell you cast each turn that mana from a Treasure was spent to cast has cascade. " +
                    "<i>(When you cast the spell, exile cards from the top of your library until you exile a nonland card that costs less. " +
                    "You may cast it without paying its mana cost. " +
                    "Put the exiled cards on the bottom of your library in a random order.)</i>";
    }

    private RainOfRichesGainsCascadeEffect(final RainOfRichesGainsCascadeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        RainOfRichesWatcher watcher = game.getState().getWatcher(RainOfRichesWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        for (StackObject stackObject : game.getStack()) {
            // Only spells cast, so no copies of spells
            if ((stackObject instanceof Spell)
                    && !stackObject.isCopy()
                    && stackObject.isControlledBy(source.getControllerId())) {
                Spell spell = (Spell) stackObject;

                if (FirstSpellCastWithTreasureCondition.instance.apply(game, source)) {
                    game.getState().addOtherAbility(spell.getCard(), cascadeAbility);
                    return true;  // TODO: I think this should return here as soon as it finds the first one.
                                  //       If it should, change WildMageSorcerer to also return early.
                }
            }
        }
        return false;
    }

    @Override
    public RainOfRichesGainsCascadeEffect copy() {
        return new RainOfRichesGainsCascadeEffect(this);
    }
}

enum FirstSpellCastWithTreasureCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getStack().isEmpty()) {
            return false;
        }
        RainOfRichesWatcher watcher = game.getState().getWatcher(RainOfRichesWatcher.class);
        StackObject so = game.getStack().getFirst();
        return watcher != null && RainOfRichesWatcher.checkSpell(so, game);
    }
}

class RainOfRichesWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerMap = new HashMap<>();

    RainOfRichesWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CAST_SPELL) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell == null) {
            return;
        }
        int manaPaid = ManaPaidSourceWatcher.getTreasurePaid(spell.getId(), game);
        if (manaPaid < 1) {
            return;
        }

        playerMap.computeIfAbsent(event.getPlayerId(), x -> new MageObjectReference(spell.getMainCard(), game));
    }

    @Override
    public void reset() {
        playerMap.clear();
        super.reset();
    }

    static boolean checkSpell(StackObject stackObject, Game game) {
        if (stackObject.isCopy()
                || !(stackObject instanceof Spell)) {
            return false;
        }
        RainOfRichesWatcher watcher = game.getState().getWatcher(RainOfRichesWatcher.class);
        return watcher.playerMap.containsKey(stackObject.getControllerId())
                && watcher.playerMap.get(stackObject.getControllerId()).refersTo(((Spell) stackObject).getMainCard(), game);
    }
}