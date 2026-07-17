package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DoombotHarbinger extends CardImpl {

    public DoombotHarbinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ROBOT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, you may mill four cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(4), true));

        // When this creature dies, you may exile this card. When you do, return target creature card from your graveyard to your hand.
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(
            new ReturnFromGraveyardToHandTargetEffect(),
            false
        );
        reflexiveAbility.addTarget(
            new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        );
        this.addAbility(new DiesSourceTriggeredAbility(
            new DoWhenCostPaid(
                reflexiveAbility,
                new ExileSourceFromGraveCost().setText("exile this card"),
                "Exile {this} to return target creature card from your graveyard to your hand?"
            )
        ));
    }

    private DoombotHarbinger(final DoombotHarbinger card) {
        super(card);
    }

    @Override
    public DoombotHarbinger copy() {
        return new DoombotHarbinger(this);
    }
}
