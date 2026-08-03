package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 * @author muz
 */
public final class NighthowlPursuer extends CardImpl {

    public NighthowlPursuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Menace
        this.addAbility(new MenaceAbility());

        // Ferocious -- Whenever this creature attacks while you control a creature with power 4 or greater, this creature gets +2/+2 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(
            new BoostSourceEffect(2, 2, Duration.EndOfTurn).setText("this creature gets +2/+2 until end of turn"))
                .withTriggerCondition(FerociousCondition.instance)
                .setAbilityWord(AbilityWord.FEROCIOUS)
                .addHint(FerociousHint.instance)
        );
    }

    private NighthowlPursuer(final NighthowlPursuer card) {
        super(card);
    }

    @Override
    public NighthowlPursuer copy() {
        return new NighthowlPursuer(this);
    }
}
