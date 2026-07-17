package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SoaringStoneglider extends CardImpl {

    public SoaringStoneglider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ELEPHANT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // As an additional cost to cast this spell, exile two cards from your graveyard or pay {1}{W}.
        this.getSpellAbility().addCost(
            new OrCost(
                "exile two cards from your graveyard or pay {1}{W}",
                new ExileFromGraveCost(
                    new TargetCardInYourGraveyard(2, StaticFilters.FILTER_CARDS_FROM_YOUR_GRAVEYARD)
                ),
                new ManaCostsImpl<>("{1}{W}")
            )
        );

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());
    }

    private SoaringStoneglider(final SoaringStoneglider card) {
        super(card);
    }

    @Override
    public SoaringStoneglider copy() {
        return new SoaringStoneglider(this);
    }
}
