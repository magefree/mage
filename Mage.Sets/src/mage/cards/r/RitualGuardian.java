package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RitualGuardian extends CardImpl {

    public RitualGuardian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, Ritual Guardian gains lifelink until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(
                        new GainAbilitySourceEffect(
                                LifelinkAbility.getInstance(), Duration.EndOfTurn
                        ), TargetController.YOU, false
                ), CovenCondition.instance, "At the beginning of combat on your turn, if you control three " +
                "or more creatures with different powers, {this} gains lifelink until end of turn."
        ).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private RitualGuardian(final RitualGuardian card) {
        super(card);
    }

    @Override
    public RitualGuardian copy() {
        return new RitualGuardian(this);
    }
}
