package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
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
public final class SkywarpSkaab extends CardImpl {

    public SkywarpSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Skywarp Skaab enters the battlefield, you may exile two creature cards from your graveyard. If you do, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(
                        new DrawCardSourceControllerEffect(1),
                        new ExileFromGraveCost(new TargetCardInYourGraveyard(
                                2, StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD
                        ))
                )
        ));
    }

    private SkywarpSkaab(final SkywarpSkaab card) {
        super(card);
    }

    @Override
    public SkywarpSkaab copy() {
        return new SkywarpSkaab(this);
    }
}
