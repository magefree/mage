package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.dynamicvalue.common.StrangeNewWorldsValue;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.StrangeNewWorldsHint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author muz
 */
public final class PahWraith extends CardImpl {

    public PahWraith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.subtype.add(SubType.WRAITH);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // First strike, haste
        this.addAbility(FirstStrikeAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());

        // Strange new worlds -- Whenever this creature attacks, creatures you control get +X/+0 until end of turn, where X is the number of differently named lands you control.
        this.addAbility(new AttacksTriggeredAbility(new BoostControlledEffect(
            StrangeNewWorldsValue.instance, StaticValue.get(0), Duration.EndOfTurn
        ).setText("creatures you control get +X/+0 until end of turn, where X is the number of differently named lands you control"))
            .setAbilityWord(AbilityWord.STRANGE_NEW_WORLDS)
            .addHint(StrangeNewWorldsHint.instance)
        );
    }

    private PahWraith(final PahWraith card) {
        super(card);
    }

    @Override
    public PahWraith copy() {
        return new PahWraith(this);
    }
}
