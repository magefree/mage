
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class SlaughterhouseBouncer extends CardImpl {

    public SlaughterhouseBouncer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Hellbent - When Slaughterhouse Bouncer dies, if you have no cards in hand, target creature gets -3/-3 until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new DiesSourceTriggeredAbility(new BoostTargetEffect(-3, -3, Duration.EndOfTurn)),
                HellbentCondition.instance,
                AbilityWord.HELLBENT.formatWord() + "When {this} dies, if you have no cards in hand, target creature gets -3/-3 until end of turn."
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private SlaughterhouseBouncer(final SlaughterhouseBouncer card) {
        super(card);
    }

    @Override
    public SlaughterhouseBouncer copy() {
        return new SlaughterhouseBouncer(this);
    }
}
