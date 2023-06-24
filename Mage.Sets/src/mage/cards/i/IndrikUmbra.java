
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.TotemArmorAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class IndrikUmbra extends CardImpl {

    public IndrikUmbra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{G}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +4/+4 and has first strike, and all creatures able to block it do so.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(4, 4, Duration.WhileOnBattlefield));
        ability.addEffect(new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA));
        ability.addEffect(new MustBeBlockedByAllAttachedEffect(AttachmentType.AURA));
        this.addAbility(ability);

        // Totem armor
        this.addAbility(new TotemArmorAbility());
    }

    private IndrikUmbra(final IndrikUmbra card) {
        super(card);
    }

    @Override
    public IndrikUmbra copy() {
        return new IndrikUmbra(this);
    }
}
