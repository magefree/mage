package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksAloneControlledTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class BattlegraceAngel extends CardImpl {

    public BattlegraceAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Exalted (Whenever a creature you control attacks alone, that creature gets +1/+1 until end of turn.)
        this.addAbility(new ExaltedAbility());

        // Whenever a creature you control attacks alone, it gains lifelink until end of turn.
        this.addAbility(new AttacksAloneControlledTriggeredAbility(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("it gains lifelink until end of turn"), true, false));
    }

    public BattlegraceAngel(final BattlegraceAngel card) {
        super(card);
    }

    @Override
    public BattlegraceAngel copy() {
        return new BattlegraceAngel(this);
    }

}
