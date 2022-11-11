package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.MayCastFromGraveyardSourceAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author Alvin
 */
public final class SkaabRuinator extends CardImpl {

    public SkaabRuinator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // As an additional cost to cast Skaab Ruinator, exile three creature cards from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(new TargetCardInYourGraveyard(
                3, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD
        )));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You may cast Skaab Ruinator from your graveyard.
        this.addAbility(new MayCastFromGraveyardSourceAbility());
    }

    private SkaabRuinator(final SkaabRuinator card) {
        super(card);
    }

    @Override
    public SkaabRuinator copy() {
        return new SkaabRuinator(this);
    }
}
