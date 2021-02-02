
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author anonymous
 */
public final class ForgeDevil extends CardImpl {

    public ForgeDevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}");
        this.subtype.add(SubType.DEVIL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Forge Devil enters the battlefield, it deals 1 damage to target creature and 1 damage to you.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("it deals 1 damage to target creature");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect);
        ability.addTarget(new TargetCreaturePermanent());
        effect = new DamageControllerEffect(1);
        effect.setText("and 1 damage to you");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private ForgeDevil(final ForgeDevil card) {
        super(card);
    }

    @Override
    public ForgeDevil copy() {
        return new ForgeDevil(this);
    }
}
