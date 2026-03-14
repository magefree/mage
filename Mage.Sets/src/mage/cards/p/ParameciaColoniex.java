package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ParameciaColoniex extends CardImpl {

    public ParameciaColoniex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WORM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When this creature enters, mill three cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new MillCardsControllerEffect(3)));

        // When this creature dies, you may exile it. When you do, put target creature card from your graveyard on top of your library.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
            new PutOnLibraryTargetEffect(true), false
        );
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new DiesSourceTriggeredAbility(new DoWhenCostPaid(
            ability, new ExileSourceFromGraveCost().setText("exile it"), "Exile {this}?"
        )));
    }

    private ParameciaColoniex(final ParameciaColoniex card) {
        super(card);
    }

    @Override
    public ParameciaColoniex copy() {
        return new ParameciaColoniex(this);
    }
}
