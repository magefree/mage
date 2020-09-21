package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.MultipliedValue;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DranasSilencer extends CardImpl {

    private static final DynamicValue xValue = new MultipliedValue(PartyCount.instance, -1);

    public DranasSilencer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Drana's Silencer enters the battlefield, target creature an opponent controls gets -X/-X until end of turn, where X is the number of creatures in your party.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(
                xValue, xValue, Duration.EndOfTurn, true
        ).setText("target creature an opponent controls gets -X/-X until end of turn, " +
                "where X is the number of creatures in your party. " + PartyCount.getReminder()));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability.addHint(PartyCountHint.instance));
    }

    private DranasSilencer(final DranasSilencer card) {
        super(card);
    }

    @Override
    public DranasSilencer copy() {
        return new DranasSilencer(this);
    }
}
