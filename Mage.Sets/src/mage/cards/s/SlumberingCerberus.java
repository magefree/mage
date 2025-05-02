package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.common.DontUntapInControllersUntapStepSourceEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SlumberingCerberus extends CardImpl {

    public SlumberingCerberus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // This creature doesn't untap during your untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepSourceEffect()));

        // Morbid -- At the beginning of each end step, if a creature died this turn, untap this creature.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.ANY, new UntapSourceEffect(), false, MorbidCondition.instance
        ).setAbilityWord(AbilityWord.MORBID).addHint(MorbidHint.instance));
    }

    private SlumberingCerberus(final SlumberingCerberus card) {
        super(card);
    }

    @Override
    public SlumberingCerberus copy() {
        return new SlumberingCerberus(this);
    }
}
