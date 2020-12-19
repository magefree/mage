package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
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
import mage.game.Game;
import mage.players.Player;
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

    public ArmixFiligreeThrasher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Armix, Filigree Thrasher attacks, you may discard a card. When you do, target creature defending player controls gets -X/-X until end of turn, where X is the number of artifacts you control plus the number of artifact cards in your graveyard.
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(
                new BoostTargetEffect(
                        ArmixFiligreeThrasherValue.instance,
                        ArmixFiligreeThrasherValue.instance,
                        Duration.EndOfTurn, true
                ), false, "target creature defending player controls gets -X/-X until end of turn, " +
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

enum ArmixFiligreeThrasherValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Player player = game.getPlayer(sourceAbility.getControllerId());
        if (player == null) {
            return 0;
        }
        return -(player.getGraveyard().count(
                StaticFilters.FILTER_CARD_ARTIFACT, player.getId(), game
        ) + game.getBattlefield().count(
                StaticFilters.FILTER_PERMANENT_ARTIFACT,
                sourceAbility.getSourceId(),
                sourceAbility.getControllerId(), game
        ));
    }

    @Override
    public ArmixFiligreeThrasherValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
