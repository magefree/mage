package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.HumanWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MajaBretagardProtector extends CardImpl {

    public MajaBretagardProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, true
        )));

        // Whenever a land enters the battlefield under your control, create a 1/1 white Human Warrior creature token.
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new HumanWarriorToken())));
    }

    private MajaBretagardProtector(final MajaBretagardProtector card) {
        super(card);
    }

    @Override
    public MajaBretagardProtector copy() {
        return new MajaBretagardProtector(this);
    }
}
