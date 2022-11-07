package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SkyfisherSpider extends CardImpl {

    private static final DynamicValue xValue = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);

    public SkyfisherSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Skyfisher Spider enters the battlefield, you may sacrifice another creature. When you do, destroy target nonland permanent.
        ReflexiveTriggeredAbility rAbility = new ReflexiveTriggeredAbility(new DestroyTargetEffect(), false);
        rAbility.addTarget(new TargetNonlandPermanent());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DoWhenCostPaid(
                rAbility,
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE),
                "Sacrifice another creature?"
        )));

        // When Skyfisher Spider dies, you may gain 1 life for each creature card in your graveyard. If you do, exile Skyfisher Spider from your graveyard.
        Ability ability = new DiesSourceTriggeredAbility(
                new GainLifeEffect(xValue)
                        .setText("gain 1 life for each creature card in your graveyard"),
                true
        );
        ability.addEffect(new ExileSourceEffect().setText("if you do, exile {this} from your graveyard"));
        this.addAbility(ability);
    }

    private SkyfisherSpider(final SkyfisherSpider card) {
        super(card);
    }

    @Override
    public SkyfisherSpider copy() {
        return new SkyfisherSpider(this);
    }
}
