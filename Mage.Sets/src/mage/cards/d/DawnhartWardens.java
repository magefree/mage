package mage.cards.d;

import mage.MageInt;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DawnhartWardens extends CardImpl {

    public DawnhartWardens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Coven — At the beginning of combat on your turn, if you control three or more creatures with different powers, creatures you control get +1/+0 until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new BoostControlledEffect(1, 0, Duration.EndOfTurn))
                .withInterveningIf(CovenCondition.instance).addHint(CovenHint.instance).setAbilityWord(AbilityWord.COVEN));
    }

    private DawnhartWardens(final DawnhartWardens card) {
        super(card);
    }

    @Override
    public DawnhartWardens copy() {
        return new DawnhartWardens(this);
    }
}
