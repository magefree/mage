package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.AdventureCard;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MerchantOfTheVale extends AdventureCard {

    public MerchantOfTheVale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{2}{R}",
                "Haggle",
                new CardType[]{CardType.INSTANT}, "{R}");

        // Merchant of the Vale
        this.getLeftHalfCard().setPT(2, 3);

        // {2}{R}, Discard a card: Draw a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{2}{R}")
        );
        ability.addCost(new DiscardCardCost());
        this.getLeftHalfCard().addAbility(ability);

        // Haggle
        // You may discard a card. If you do, draw a card.
        this.getRightHalfCard().getSpellAbility().addEffect(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        ));

        finalizeCard();
    }

    private MerchantOfTheVale(final MerchantOfTheVale card) {
        super(card);
    }

    @Override
    public MerchantOfTheVale copy() {
        return new MerchantOfTheVale(this);
    }
}
