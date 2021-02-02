
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.RevoltCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ServoToken;
import mage.watchers.common.RevoltWatcher;

/**
 * @author JRHerlehy
 */
public final class CountlessGearsRenegade extends CardImpl {

    public CountlessGearsRenegade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // <i>Revolt</i> &mdash; When Countless Gears Renegade enters the battlefield, if a permanent you controlled
        // left the battlefield this turn, create a 1/1 colorless Servo artifact creature token.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new ServoToken(), 1), false), RevoltCondition.instance,
                "<i>Revolt</i> &mdash; When {this} enters the battlefield, if a permanent you controlled left"
                + " the battlefield this turn, create a 1/1 colorless Servo artifact creature token.");
        ability.setAbilityWord(AbilityWord.REVOLT);
        ability.addWatcher(new RevoltWatcher());
        this.addAbility(ability);
    }

    private CountlessGearsRenegade(final CountlessGearsRenegade card) {
        super(card);
    }

    @Override
    public CountlessGearsRenegade copy() {
        return new CountlessGearsRenegade(this);
    }
}
