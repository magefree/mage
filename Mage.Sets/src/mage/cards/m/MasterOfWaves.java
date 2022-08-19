package mage.cards.m;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DevotionCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.MasterOfWavesElementalToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class MasterOfWaves extends CardImpl {

    private static final FilterCreaturePermanent filterBoost
            = new FilterCreaturePermanent(SubType.ELEMENTAL, "Elemental creatures");

    public MasterOfWaves(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from red
        this.addAbility(ProtectionAbility.from(ObjectColor.RED));

        // Elemental creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filterBoost, false
        )));

        // When Master of Waves enters the battlefield, create a number of 1/0 blue Elemental creature tokens equal to your devotion to blue.
        // <i>(Each {U} in the mana costs of permanents you control counts toward your devotion to blue.)</i>
        Effect effect = new CreateTokenEffect(new MasterOfWavesElementalToken(), DevotionCount.U);
        effect.setText("create a number of 1/0 blue Elemental creature tokens equal to your devotion to blue. " + DevotionCount.U.getReminder());
        this.addAbility(new EntersBattlefieldTriggeredAbility(effect).addHint(DevotionCount.U.getHint()));
    }

    private MasterOfWaves(final MasterOfWaves card) {
        super(card);
    }

    @Override
    public MasterOfWaves copy() {
        return new MasterOfWaves(this);
    }
}
