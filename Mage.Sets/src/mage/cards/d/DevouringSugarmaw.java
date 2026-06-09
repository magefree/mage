package mage.cards.d;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.FoodToken;
import mage.game.permanent.token.HumanToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DevouringSugarmaw extends AdventureCard {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent("an artifact, enchantment, or token");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.ENCHANTMENT.getPredicate(),
                TokenPredicate.TRUE
        ));
    }

    public DevouringSugarmaw(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HORROR}, "{2}{B}{B}",
                "Have for Dinner",
                new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Devouring Sugarmaw
        this.getLeftHalfCard().setPT(6, 6);

        // Menace
        this.getLeftHalfCard().addAbility(new MenaceAbility(false));

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        //  At the beginning of your upkeep, you may sacrifice an artifact, enchantment, or token. If you don't, tap Devouring Sugarmaw.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        null,
                        new TapSourceEffect(),
                        new SacrificeTargetCost(filter),
                        true
                )
        ));

        // Have for Dinner
        // Create a 1/1 white Human creature token and a Food token.
        this.getRightHalfCard().getSpellAbility().addEffect(new CreateTokenEffect(new HumanToken()).withAdditionalTokens(new FoodToken()));

        finalizeCard();
    }

    private DevouringSugarmaw(final DevouringSugarmaw card) {
        super(card);
    }

    @Override
    public DevouringSugarmaw copy() {
        return new DevouringSugarmaw(this);
    }
}
