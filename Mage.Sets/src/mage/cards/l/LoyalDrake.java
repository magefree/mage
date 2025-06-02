package mage.cards.l;

import mage.MageInt;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoyalDrake extends CardImpl {

    public LoyalDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, draw a card.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new DrawCardSourceControllerEffect(1))
                .withInterveningIf(ControlYourCommanderCondition.instance)
                .setAbilityWord(AbilityWord.LIEUTENANT));
    }

    private LoyalDrake(final LoyalDrake card) {
        super(card);
    }

    @Override
    public LoyalDrake copy() {
        return new LoyalDrake(this);
    }
}
