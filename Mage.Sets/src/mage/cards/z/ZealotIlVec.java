
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAndIsNotBlockedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.keyword.ShadowAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LoneFox
 */
public final class ZealotIlVec extends CardImpl {

    public ZealotIlVec(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN, SubType.REBEL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Shadow
        this.addAbility(ShadowAbility.getInstance());
        // Whenever Zealot il-Vec attacks and isn't blocked, you may have it deal 1 damage to target creature. If you do, prevent all combat damage Zealot il-Vec would deal this turn.
        Ability ability = new AttacksAndIsNotBlockedTriggeredAbility(new DamageTargetEffect(1).setText("it deal 1 damage to target creature"), true);
        Effect effect = new PreventCombatDamageBySourceEffect(Duration.EndOfTurn);
        effect.setText("if you do, prevent all combat damage {this} would deal this turn");
        ability.addEffect(effect);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

    }

    private ZealotIlVec(final ZealotIlVec card) {
        super(card);
    }

    @Override
    public ZealotIlVec copy() {
        return new ZealotIlVec(this);
    }
}
