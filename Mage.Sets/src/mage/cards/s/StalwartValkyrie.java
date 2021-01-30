package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StalwartValkyrie extends CardImpl {

    public StalwartValkyrie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // You may pay {1}{W} and exile a creature card from your graveyard rather than pay this spell's mana cost.
        Ability ability = new AlternativeCostSourceAbility(new ManaCostsImpl<>("{1}{W}"));
        ability.addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_A)));
        this.addAbility(ability);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private StalwartValkyrie(final StalwartValkyrie card) {
        super(card);
    }

    @Override
    public StalwartValkyrie copy() {
        return new StalwartValkyrie(this);
    }
}
