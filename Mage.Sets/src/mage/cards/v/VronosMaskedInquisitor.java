package mage.cards.v;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.PhaseOutTargetEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.AddCardSubTypeTargetEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class VronosMaskedInquisitor extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter = new FilterControlledPlaneswalkerPermanent("planeswalkers you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public VronosMaskedInquisitor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VRONOS);
        this.setStartingLoyalty(5);

        // +1: Up to two other target planeswalkers you control phase out at the beginning of the next end step.
        LoyaltyAbility ability = new LoyaltyAbility(new CreateDelayedTriggeredAbilityEffect(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new PhaseOutTargetEffect()))
                .setText("up to two other target planeswalkers you control phase out at the beginning of the next end step. "
                        + "<i>(Treat them and anything attached to them as though they don't exist until your next turn.)</i>"), 1);
        ability.addTarget(new TargetPermanent(0, 2, filter));
        this.addAbility(ability);

        // −2: For each opponent, return up to one target nonland permanent that player controls to its owner's hand.
        LoyaltyAbility ability2 = new LoyaltyAbility(new ReturnToHandTargetEffect().setTargetPointer(new EachTargetPointer())
                .setText("for each opponent, return up to one target nonland permanent that player controls to its owner's hand"), -2);
        ability2.setTargetAdjuster(VronosMaskedInquisitorAdjuster.instance);
        this.addAbility(ability2);

        // −7: Target artifact you control becomes a 9/9 Construct artifact creature and gains vigilance, indestructible, and "This creature can't be blocked."
        LoyaltyAbility ability3 = new LoyaltyAbility(new BecomesCreatureTargetEffect(
                new CreatureToken(9, 9)
                        .withType(CardType.ARTIFACT),
                false, false, Duration.EndOfGame).setText("Target artifact you control becomes a 9/9 Construct artifact creature"), -7);
        ability3.addEffect(new AddCardSubTypeTargetEffect(SubType.CONSTRUCT, Duration.EndOfGame).setText(" and gains"));
        ability3.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance(), Duration.EndOfGame, " vigilance,"));
        ability3.addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfGame, " indestructible,"));
        ability3.addEffect(new GainAbilityTargetEffect(new SimpleStaticAbility(new CantBeBlockedSourceEffect()), Duration.EndOfGame, " and \"This creature can't be blocked.\""));
        ability3.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT));
        this.addAbility(ability3);
    }

    private VronosMaskedInquisitor(final VronosMaskedInquisitor card) {
        super(card);
    }

    @Override
    public VronosMaskedInquisitor copy() {
        return new VronosMaskedInquisitor(this);
    }
}

enum VronosMaskedInquisitorAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterNonlandPermanent("nonland permanent controlled by " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponentId));
            ability.addTarget(new TargetPermanent(0, 1, filter, false));
        }
    }
}
