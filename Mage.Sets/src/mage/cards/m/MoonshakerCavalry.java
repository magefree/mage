package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MoonshakerCavalry extends CardImpl {

    public MoonshakerCavalry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{W}{W}{W}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Moonshaker Cavalry enters the battlefield, creatures you control gain flying and get +X/+X until end of turn, where X is the number of creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainAbilityControlledEffect(
                FlyingAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        ).setText("creatures you control gain flying"));
        ability.addEffect(new BoostControlledEffect(
                CreaturesYouControlCount.instance, CreaturesYouControlCount.instance, Duration.EndOfTurn
        ).setText("and get +X/+X until end of turn, where X is the number of creatures you control"));
        this.addAbility(ability.addHint(CreaturesYouControlHint.instance));
    }

    private MoonshakerCavalry(final MoonshakerCavalry card) {
        super(card);
    }

    @Override
    public MoonshakerCavalry copy() {
        return new MoonshakerCavalry(this);
    }
}
