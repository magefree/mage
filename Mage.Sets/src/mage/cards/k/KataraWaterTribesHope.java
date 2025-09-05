package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AllyToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KataraWaterTribesHope extends CardImpl {

    public KataraWaterTribesHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Katara enters, create a 1/1 white Ally creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AllyToken())));

        // Waterbend {X}: Creatures you control have base power and toughness X/X until end of turn. X can't be 0. Activate only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(new SetBasePowerToughnessAllEffect(
                GetXValue.instance, GetXValue.instance, Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES
        ), new WaterbendCost("{X}"), MyTurnCondition.instance);
        CardUtil.castStream(ability.getCosts(), VariableManaCost.class).forEach(cost -> cost.setMinX(1));
        this.addAbility(ability);
    }

    private KataraWaterTribesHope(final KataraWaterTribesHope card) {
        super(card);
    }

    @Override
    public KataraWaterTribesHope copy() {
        return new KataraWaterTribesHope(this);
    }
}
