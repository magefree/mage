package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.FirstCombatPhaseCondition;
import mage.abilities.effects.common.AdditionalCombatPhaseEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphAndLeoSiblingRivals extends CardImpl {

    public RaphAndLeoSiblingRivals(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R/W}{R/W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.NINJA);
        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Whenever Raph & Leo attack, if it's the first combat phase of the turn, untap one or two target attacking creatures. After this phase, there is an additional combat phase.
        Ability ability = new AttacksTriggeredAbility(new UntapTargetEffect())
                .withInterveningIf(FirstCombatPhaseCondition.instance)
                .setTriggerPhrase("Whenever {this} attack, ");
        ability.addEffect(new AdditionalCombatPhaseEffect());
        ability.addTarget(new TargetAttackingCreature(1, 2));
        this.addAbility(ability);
    }

    private RaphAndLeoSiblingRivals(final RaphAndLeoSiblingRivals card) {
        super(card);
    }

    @Override
    public RaphAndLeoSiblingRivals copy() {
        return new RaphAndLeoSiblingRivals(this);
    }
}
