package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FerociousCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.FerociousHint;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OrneryDilophosaur extends CardImpl {

    public OrneryDilophosaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever Ornery Dilophosaur attacks, if you control a creature with power 4 or greater, Ornery Dilophosaur gets +2/+2 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new BoostSourceEffect(2, 2, Duration.EndOfTurn), false
                ), FerociousCondition.instance, "Whenever {this} attacks, " +
                "if you control a creature with power 4 or greater, {this} gets +2/+2 until end of turn."
        ).addHint(FerociousHint.instance));
    }

    private OrneryDilophosaur(final OrneryDilophosaur card) {
        super(card);
    }

    @Override
    public OrneryDilophosaur copy() {
        return new OrneryDilophosaur(this);
    }
}
