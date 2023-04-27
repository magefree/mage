package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.AdditiveDynamicValue;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.SignInversionDynamicValue;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ArmixFiligreeThrasher extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
    }

    private static final DynamicValue xValue = new SignInversionDynamicValue(new AdditiveDynamicValue(
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACTS),
            new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_ARTIFACT)
    ));

    public ArmixFiligreeThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Armix, Filigree Thrasher attacks, you may discard a card. When you do, target creature defending player controls gets -X/-X until end of turn, where X is the number of artifacts you control plus the number of artifact cards in your graveyard.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(xValue, xValue, Duration.EndOfTurn), false,
                "target creature defending player controls gets -X/-X until end of turn, " +
                "where X is the number of artifacts you control plus the number of artifact cards in your graveyard"
        );
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new AttacksTriggeredAbility(new DoWhenCostPaid(
                ability, new DiscardCardCost(), "Discard a card?"
        ), false));

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private ArmixFiligreeThrasher(final ArmixFiligreeThrasher card) {
        super(card);
    }

    @Override
    public ArmixFiligreeThrasher copy() {
        return new ArmixFiligreeThrasher(this);
    }
}
