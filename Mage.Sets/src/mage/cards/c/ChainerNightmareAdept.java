package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.Watcher;
import mage.watchers.common.CastFromHandWatcher;

/**
 * @author goesta
 */
public final class ChainerNightmareAdept extends CardImpl {

    public ChainerNightmareAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Discard a card: You may cast a creature card from your graveyard this turn. Activate this ability only once each turn.
        Ability ability = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new ChainerNightmareAdeptContinuousEffect(), new DiscardCardCost());
        this.addAbility(ability, new ChainerNightmareAdeptWatcher());

        // Whenever a nontoken creature enters the battlefield under your control, if you didn't cast it from your hand, it gains haste until your next turn.
        this.addAbility(new ChainerNightmareAdeptTriggeredAbility(), new CastFromHandWatcher());
    }

    private ChainerNightmareAdept(final ChainerNightmareAdept card) {
        super(card);
    }

    @Override
    public ChainerNightmareAdept copy() {
        return new ChainerNightmareAdept(this);
    }
}

class ChainerNightmareAdeptContinuousEffect extends ContinuousEffectImpl {

    ChainerNightmareAdeptContinuousEffect() {
        super(Duration.EndOfTurn, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may cast a creature card from your graveyard this turn.";
    }

    ChainerNightmareAdeptContinuousEffect(final ChainerNightmareAdeptContinuousEffect effect) {
        super(effect);
    }

    @Override
    public ChainerNightmareAdeptContinuousEffect copy() {
        return new ChainerNightmareAdeptContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());

        if (player == null || game.getActivePlayerId() == null || !game.isActivePlayer(player.getId())) {
            return false;
        }

        for (Card card : player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            ContinuousEffect effect = new ChainerNightmareAdeptCastFromGraveyardEffect();
            effect.setTargetPointer(new FixedTarget(card.getId()));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class ChainerNightmareAdeptCastFromGraveyardEffect extends AsThoughEffectImpl {

    ChainerNightmareAdeptCastFromGraveyardEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You may cast one creature card from your graveyard";
    }

    ChainerNightmareAdeptCastFromGraveyardEffect(final ChainerNightmareAdeptCastFromGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ChainerNightmareAdeptCastFromGraveyardEffect copy() {
        return new ChainerNightmareAdeptCastFromGraveyardEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Card card = game.getCard(objectId);
        if (card != null
                && card.getId().equals(getTargetPointer().getFirst(game, source))
                && card.isCreature()
                && card.getSpellAbility() != null
                && affectedControllerId != null
                && card.getSpellAbility().spellCanBeActivatedRegularlyNow(affectedControllerId, game)
                && affectedControllerId.equals(source.getControllerId())) {
            ChainerNightmareAdeptWatcher watcher = game.getState().getWatcher(ChainerNightmareAdeptWatcher.class, source.getSourceId());
            return watcher != null && !watcher.isAbilityUsed();
        }
        return false;
    }
}

class ChainerNightmareAdeptWatcher extends Watcher {

    private boolean abilityUsed = false;

    ChainerNightmareAdeptWatcher() {
        super(WatcherScope.CARD);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && event.getZone() == Zone.GRAVEYARD) {
            Spell spell = (Spell) game.getObject(event.getTargetId());
            if (spell.isCreature()) {
                abilityUsed = true;
            }
        }
    }

    @Override
    public void reset() {
        super.reset();
        abilityUsed = false;
    }

    public boolean isAbilityUsed() {
        return abilityUsed;
    }
}

class ChainerNightmareAdeptTriggeredAbility extends EntersBattlefieldAllTriggeredAbility {

    private final static String abilityText = "Whenever a nontoken creature enters the battlefield under your control, "
            + "if you didn't cast it from your hand, it gains haste until your next turn.";
    private final static ContinuousEffect gainHasteUntilNextTurnEffect = new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.UntilYourNextTurn);
    private final static FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("another nontoken creature");

    static {
        filter.add(Predicates.not(TokenPredicate.instance));
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(AnotherPredicate.instance);
    }

    public ChainerNightmareAdeptTriggeredAbility() {
        super(Zone.BATTLEFIELD, gainHasteUntilNextTurnEffect, filter, false, SetTargetPointer.PERMANENT, abilityText);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        return watcher != null && !watcher.spellWasCastFromHand(event.getSourceId());
    }
}
