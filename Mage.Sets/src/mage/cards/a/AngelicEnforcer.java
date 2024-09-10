package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ControllerLifeCount;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicEnforcer extends CardImpl {

    public AngelicEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setWhite(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You have hexproof.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControllerEffect(HexproofAbility.getInstance())));

        // Angelic Enforcer's power and toughness are each equal to your life total.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(
                ControllerLifeCount.instance
        ).setText("{this}'s power and toughness are each equal to your life total")));

        // Whenever Angelic Enforcer attacks, double your life total.
        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(
                ControllerLifeCount.instance
        ).setText("double your life total")));
    }

    private AngelicEnforcer(final AngelicEnforcer card) {
        super(card);
    }

    @Override
    public AngelicEnforcer copy() {
        return new AngelicEnforcer(this);
    }
}
