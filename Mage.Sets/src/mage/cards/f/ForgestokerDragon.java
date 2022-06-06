
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.SourceAttackingCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ForgestokerDragon extends CardImpl {

    public ForgestokerDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{R}");
        this.subtype.add(SubType.DRAGON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // {1}{R}: Forgestoker Dragon deals 1 damage to target creature. That creature can't block this combat. Activate this ability only if Forgestoker Dragon is attacking.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new DamageTargetEffect(1), new ManaCostsImpl<>("{1}{R}"), SourceAttackingCondition.instance);
        ability.addTarget(new TargetCreaturePermanent());
        Effect effect = new CantBlockTargetEffect(Duration.EndOfCombat);
        effect.setText("That creature can't block this combat");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private ForgestokerDragon(final ForgestokerDragon card) {
        super(card);
    }

    @Override
    public ForgestokerDragon copy() {
        return new ForgestokerDragon(this);
    }
}
