package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.VoidWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Hylderblade extends CardImpl {

    public Hylderblade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{B}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(3, 1)));

        // Void -- At the beginning of your end step, if a nonland permanent left the battlefield this turn or a spell was warped this turn, attach this Equipment to target creature you control.
        Ability ability = new BeginningOfEndStepTriggeredAbility(new AttachEffect(
                Outcome.BoostCreature, "attach {this} to target creature you control"
        )).withInterveningIf(VoidCondition.instance);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());

        // Equip {4}
        this.addAbility(new EquipAbility(4));
    }

    private Hylderblade(final Hylderblade card) {
        super(card);
    }

    @Override
    public Hylderblade copy() {
        return new Hylderblade(this);
    }
}
