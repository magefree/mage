
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class CartoucheOfZeal extends CardImpl {

    public CartoucheOfZeal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}");

        this.subtype.add(SubType.AURA, SubType.CARTOUCHE);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When Cartouche of Zeal enters the battlefield, target creature can't block this turn.
        ability = new EntersBattlefieldTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1 and has haste.
        ability =new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(HasteAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has haste");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private CartoucheOfZeal(final CartoucheOfZeal card) {
        super(card);
    }

    @Override
    public CartoucheOfZeal copy() {
        return new CartoucheOfZeal(this);
    }
}
