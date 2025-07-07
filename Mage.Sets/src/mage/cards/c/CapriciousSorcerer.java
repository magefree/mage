package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnBeforeAttackersDeclaredCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class CapriciousSorcerer extends CardImpl {

    public CapriciousSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Capricious Sorcerer deals 1 damage to any target. Activate this ability only during your turn, before attackers are declared.
        Ability ability = new ActivateIfConditionActivatedAbility(
                new DamageTargetEffect(1), new TapSourceCost(),
                MyTurnBeforeAttackersDeclaredCondition.instance
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private CapriciousSorcerer(final CapriciousSorcerer card) {
        super(card);
    }

    @Override
    public CapriciousSorcerer copy() {
        return new CapriciousSorcerer(this);
    }
}
