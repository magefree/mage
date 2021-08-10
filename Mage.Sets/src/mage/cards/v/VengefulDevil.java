package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.MorbidHint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VengefulDevil extends CardImpl {

    public VengefulDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DEVIL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Morbid — {T}: Vengeful Devil deals 1 damage to any target. Activate this ability only if a creature died this turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new DamageTargetEffect(1),
                new TapSourceCost(), MorbidCondition.instance
        );
        ability.addTarget(new TargetAnyTarget());
        ability.setAbilityWord(AbilityWord.MORBID);
        this.addAbility(ability.addHint(MorbidHint.instance));
    }

    private VengefulDevil(final VengefulDevil card) {
        super(card);
    }

    @Override
    public VengefulDevil copy() {
        return new VengefulDevil(this);
    }
}
