
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class AlphaAuthority extends CardImpl {

    public AlphaAuthority(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has hexproof and can't be blocked by more than one creature.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(HexproofAbility.getInstance(), AttachmentType.AURA, Duration.WhileOnBattlefield));
        Effect effect = new CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType.AURA);
        effect.setText("and can't be blocked by more than one creature");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private AlphaAuthority(final AlphaAuthority card) {
        super(card);
    }

    @Override
    public AlphaAuthority copy() {
        return new AlphaAuthority(this);
    }
}
