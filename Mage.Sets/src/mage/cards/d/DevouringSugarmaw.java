package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
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
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, new CardType[]{CardType.INSTANT}, "{2}{B}{B}", "Have for Dinner", "{1}{W}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        //  At the beginning of your upkeep, you may sacrifice an artifact, enchantment, or token. If you don't, tap Devouring Sugarmaw.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DoIfCostPaid(
                        null,
                        new TapSourceEffect(),
                        new SacrificeTargetCost(filter),
                        true
                ),
                TargetController.YOU,
                false
        ));

        // Have for Dinner
        // Create a 1/1 white Human creature token and a Food token.
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new HumanToken()));
        this.getSpellCard().getSpellAbility().addEffect(new CreateTokenEffect(new FoodToken())
                .setText("and a Food token"));

        this.finalizeAdventure();
    }

    private DevouringSugarmaw(final DevouringSugarmaw card) {
        super(card);
    }

    @Override
    public DevouringSugarmaw copy() {
        return new DevouringSugarmaw(this);
    }
}
