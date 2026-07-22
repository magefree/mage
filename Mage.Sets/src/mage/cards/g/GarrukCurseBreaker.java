package mage.cards.g;

import java.util.UUID;
import mage.abilities.TriggeredAbilityImpl;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.permanent.token.Beast44TrampleToken;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.UntilYourNextTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DefenderAttackedEvent;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTargets;

/**
 *
 * @author muz
 */
public final class GarrukCurseBreaker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_GREATER, 4));
    }

    public GarrukCurseBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GARRUK);
        this.setStartingLoyalty(0);

        // Whenever a creature you control with power 4 or greater enters, draw a card.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filter
        ));

        // +2: Untap up to two target lands.
        Ability ability = new LoyaltyAbility(new UntapTargetEffect(), 2);
        ability.addTarget(new TargetPermanent(0, 2, StaticFilters.FILTER_LANDS));
        this.addAbility(ability);

        // −3: Create a 4/4 green Beast creature token with trample.
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new Beast44TrampleToken()), -3));

        // −4: Until your next turn, whenever one or more creatures attack one of your opponents, those creatures get +2/+2 and gain trample until end of turn.
        this.addAbility(new LoyaltyAbility(new GarrukCurseBreakerEffect(), -4));
    }

    private GarrukCurseBreaker(final GarrukCurseBreaker card) {
        super(card);
    }

    @Override
    public GarrukCurseBreaker copy() {
        return new GarrukCurseBreaker(this);
    }
}

class GarrukCurseBreakerEffect extends OneShotEffect {

    GarrukCurseBreakerEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "Until your next turn, whenever one or more creatures attack one of your opponents, "
            + "those creatures get +2/+2 and gain trample until end of turn";
    }

    private GarrukCurseBreakerEffect(final GarrukCurseBreakerEffect effect) {
        super(effect);
    }

    @Override
    public GarrukCurseBreakerEffect copy() {
        return new GarrukCurseBreakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return new CreateDelayedTriggeredAbilityEffect(
            new UntilYourNextTurnDelayedTriggeredAbility(new GarrukCurseBreakerTriggeredAbility())
        ).apply(game, source);
    }
}

class GarrukCurseBreakerTriggeredAbility extends TriggeredAbilityImpl {

    GarrukCurseBreakerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new BoostTargetEffect(2, 2));
        this.addEffect(new GainAbilityTargetEffect(TrampleAbility.getInstance())
            .setText("those creatures get +2/+2 and gain trample until end of turn"));
        this.setTriggerPhrase("Whenever one or more creatures attack one of your opponents, ");
    }

    private GarrukCurseBreakerTriggeredAbility(final GarrukCurseBreakerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GarrukCurseBreakerTriggeredAbility copy() {
        return new GarrukCurseBreakerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DEFENDER_ATTACKED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!game.getOpponents(this.getControllerId()).contains(event.getTargetId())) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(((DefenderAttackedEvent) event).getAttackers(game), game));
        return true;
    }
}
