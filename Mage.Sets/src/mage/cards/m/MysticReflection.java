package mage.cards.m;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import java.util.UUID;
import mage.MageItem;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.game.events.ZoneChangeGroupEvent;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;

/**
 * @author TheElk801
 */
public final class MysticReflection extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("nonlegendary creature");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public MysticReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Choose target nonlegendary creature. The next time one or more creatures or planeswalkers enter the battlefield this turn, they enter as copies of the chosen creature instead.
        this.getSpellAbility().addEffect(new MysticReflectionEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addWatcher(new MysticReflectionWatcher());

        // Foretell {U}
        this.addAbility(new ForetellAbility(this, "{U}"));
    }

    private MysticReflection(final MysticReflection card) {
        super(card);
    }

    @Override
    public MysticReflection copy() {
        return new MysticReflection(this);
    }
}

class MysticReflectionEffect extends OneShotEffect {

    MysticReflectionEffect() {
        super(Outcome.Benefit);
        staticText = "Choose target nonlegendary creature. The next time one or more creatures or planeswalkers "
                + "enter the battlefield this turn, they enter as copies of the chosen creature.";
    }

    private MysticReflectionEffect(final MysticReflectionEffect effect) {
        super(effect);
    }

    @Override
    public MysticReflectionEffect copy() {
        return new MysticReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedPermanent = game.getPermanent(source.getFirstTarget());
        // store the permanent that was targeted
        game.getState().setValue("MysticReflection" + source.getSourceId().toString(), targetedPermanent);
        MysticReflectionWatcher watcher = game.getState().getWatcher(MysticReflectionWatcher.class);
        // The sourceId must be sent to the next class, otherwise it gets lost.  Thus, the identifier parameter.
        // Otherwise, the state of the permanent that was targeted gets lost if it leaves the battlefield, etc.
        // The zone is ALL because if the targeted permanent leaves the battlefield, the replacement effect still applies.
        SimpleStaticAbility staticAbilityOnCard = new SimpleStaticAbility(Zone.ALL, new MysticReflectionReplacementEffect(watcher.getEnteredThisTurn(), source.getSourceId().toString()));
        MysticReflectionGainAbilityEffect gainAbilityEffect = new MysticReflectionGainAbilityEffect(staticAbilityOnCard);
        gainAbilityEffect.setTargetPointer(new FixedTarget(targetedPermanent.getMainCard().getId(), game));
        game.addEffect(gainAbilityEffect, source);
        return true;
    }
}

class MysticReflectionReplacementEffect extends ReplacementEffectImpl {

    final private int enteredThisTurn;
    final private String identifier;

    public MysticReflectionReplacementEffect(int enteredThisTurn, String identifier) {
        super(Duration.EndOfTurn, Outcome.Copy, false);
        this.enteredThisTurn = enteredThisTurn;
        this.identifier = identifier;
        staticText = "The next time one or more creatures or planeswalkers "
                + "enter the battlefield this turn, they enter as copies of {this}";
    }

    public MysticReflectionReplacementEffect(MysticReflectionReplacementEffect effect) {
        super(effect);
        this.enteredThisTurn = effect.enteredThisTurn;
        this.identifier = effect.identifier;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        MysticReflectionWatcher watcher = game.getState().getWatcher(MysticReflectionWatcher.class);
        if (watcher != null) {
            if (watcher.getEnteredThisTurn() > this.enteredThisTurn) {
                discard();
                return false;
            }
        }
        Permanent permanentEnteringTheBattlefield = ((EntersTheBattlefieldEvent) event).getTarget();
        Permanent targetedPermanent = (Permanent) game.getState().getValue("MysticReflection" + identifier);
        return permanentEnteringTheBattlefield != null
                && targetedPermanent != null
                && permanentEnteringTheBattlefield.isCreature(game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent targetedPermanent = (Permanent) game.getState().getValue("MysticReflection" + identifier);
        if (targetedPermanent != null) {
            game.copyPermanent(targetedPermanent, event.getTargetId(), source, null);
        }
        return false;
    }

    @Override
    public MysticReflectionReplacementEffect copy() {
        return new MysticReflectionReplacementEffect(this);
    }
}

class MysticReflectionWatcher extends Watcher {

    private int enteredThisTurn = 0;

    MysticReflectionWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.ZONE_CHANGE_GROUP) {
            return;
        }
        ZoneChangeGroupEvent zEvent = (ZoneChangeGroupEvent) event;
        if (zEvent.getToZone() != Zone.BATTLEFIELD) {
            return;
        }
        Set<Card> cards = new HashSet<>();
        cards.addAll(zEvent.getCards());
        cards.addAll(zEvent.getTokens());
        if (cards.stream()
                .filter(Objects::nonNull)
                .map(MageItem::getId)
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(p -> p.isPlaneswalker(game) || p.isCreature(game))) {
            enteredThisTurn++;
        }
    }

    @Override
    public void reset() {
        super.reset();
        enteredThisTurn = 0;
    }

    public int getEnteredThisTurn() {
        return enteredThisTurn;
    }
}

class MysticReflectionGainAbilityEffect extends ContinuousEffectImpl {

    final private Ability ability;

    public MysticReflectionGainAbilityEffect(Ability ability) {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.ability = ability;
    }

    public MysticReflectionGainAbilityEffect(final MysticReflectionGainAbilityEffect effect) {
        super(effect);
        this.ability = effect.ability;
    }

    @Override
    public MysticReflectionGainAbilityEffect copy() {
        return new MysticReflectionGainAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent targetedPermanent = (Permanent) game.getState().getValue("MysticReflection" + source.getSourceId().toString());
        // The ability must be put on the card.  If it leaves the battlefield, the replacement effect must still fire and copy its "permanent" state.
        if (targetedPermanent == null) {
            return false;
        }
        Card card = targetedPermanent.getMainCard();
        if (card != null
                && !card.getAbilities().contains(ability)) {
            game.getState().addOtherAbility(card, ability);
            return true;
        }
        return false;
    }
}
