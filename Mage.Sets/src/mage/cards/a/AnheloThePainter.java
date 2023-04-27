package mage.cards.a;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.CasualtyAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.watchers.Watcher;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Alex-Vasile
 */
public class AnheloThePainter extends CardImpl {
    public AnheloThePainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.addSubType(SubType.VAMPIRE, SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // The first instant or sorcery spell you cast each turn has casualty 2.
        this.addAbility(
                new SimpleStaticAbility(
                        Zone.BATTLEFIELD,
                        new AnheloThePainterGainCausalityEffect()),
                new AnheloThePainterWatcher()
        );
    }

    private AnheloThePainter(final AnheloThePainter card) {
        super(card);
    }

    @Override
    public AnheloThePainter copy() {
        return new AnheloThePainter(this);
    }
}

class AnheloThePainterGainCausalityEffect extends ContinuousEffectImpl {

    AnheloThePainterGainCausalityEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "The first instant or sorcery spell you cast each turn has casualty 2. " +
                "<i>(As you cast that spell, you may sacrifice a creature with power 2 or greater. " +
                "When you do, copy the spell and you may choose new targets for the copy.)</i>";
    }

    AnheloThePainterGainCausalityEffect(final AnheloThePainterGainCausalityEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        AnheloThePainterWatcher watcher = game.getState().getWatcher(AnheloThePainterWatcher.class);
        if (controller == null || watcher == null) {
            return false;
        }

        boolean applied = false;
        for (StackObject stackObject : game.getStack()) {
            if (!(stackObject instanceof Spell)
                    || stackObject.isCopy()
                    || !stackObject.isControlledBy(source.getControllerId())
                    || !AnheloThePainterWatcher.checkSpell(stackObject, game) ) {
                continue;
            }
            Spell spell = (Spell) stackObject;
            Card card = spell.getCard();
            game.getState().addOtherAbility(card, new CasualtyAbility(2));
            applied = true;
        }

        return applied;
    }

    @Override
    public AnheloThePainterGainCausalityEffect copy() {
        return new AnheloThePainterGainCausalityEffect(this);
    }
}

class AnheloThePainterWatcher extends Watcher {

    private final Map<UUID, MageObjectReference> playerMap = new HashMap<>();

    AnheloThePainterWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.CAST_SPELL) {
            return;
        }

        Spell spell = game.getSpell(event.getSourceId());
        if (spell == null || !spell.isInstantOrSorcery(game)) {
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
        if (stackObject.isCopy() || !(stackObject instanceof Spell)) {
            return false;
        }

        UUID controllerId = stackObject.getControllerId();
        AnheloThePainterWatcher watcher = game.getState().getWatcher(AnheloThePainterWatcher.class);
        return watcher.playerMap.containsKey(controllerId)
                && watcher.playerMap.get(controllerId).refersTo(((Spell) stackObject).getMainCard(), game);
    }
}