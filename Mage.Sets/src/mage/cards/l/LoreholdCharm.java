package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeOpponentsEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoreholdCharm extends CardImpl {

    private static final FilterPermanent filter = new FilterArtifactPermanent("nontoken artifact");
    private static final FilterCard filter2 = new FilterCard("artifact or creature card with mana value 2 or less from your graveyard");

    static {
        filter.add(TokenPredicate.FALSE);
        filter2.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
        filter2.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 3));
    }

    public LoreholdCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R}{W}");

        // Choose one --
        // * Each opponent sacrifices a nontoken artifact of their choice.
        this.getSpellAbility().addEffect(new SacrificeOpponentsEffect(filter));

        // * Return target artifact or creature card with mana value 2 or less from your graveyard to the battlefield.
        this.getSpellAbility().addMode(new Mode(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .addTarget(new TargetCardInYourGraveyard(filter2)));

        // * Creatures you control get +1/+1 and gain trample until end of turn.
        this.getSpellAbility().addMode(new Mode(new BoostControlledEffect(1, 1, Duration.EndOfTurn)
                .setText("creatures you control get +1/+1"))
                .addEffect(new GainAbilityAllEffect(
                        TrampleAbility.getInstance(), Duration.EndOfTurn,
                        StaticFilters.FILTER_CONTROLLED_CREATURE
                ).setText("and gain trample until end of turn")));
    }

    private LoreholdCharm(final LoreholdCharm card) {
        super(card);
    }

    @Override
    public LoreholdCharm copy() {
        return new LoreholdCharm(this);
    }
}
