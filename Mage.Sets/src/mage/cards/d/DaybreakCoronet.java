
package mage.cards.d;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class DaybreakCoronet extends CardImpl {
    
    public DaybreakCoronet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}{W}");
        this.subtype.add(SubType.AURA);


        // Enchant creature with another Aura attached to it
        FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with another Aura attached to it.");
        filter.add(new AuraAttachedPredicate(this.getId()));
        TargetPermanent auraTarget = new TargetCreaturePermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature gets +3/+3 and has first strike, vigilance, and lifelink.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 3, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FirstStrikeAbility.getInstance(), AttachmentType.AURA);
        effect.setText("and has first strike");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", vigilance");
        ability.addEffect(effect);
        effect = new GainAbilityAttachedEffect(LifelinkAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", and lifelink");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private DaybreakCoronet(final DaybreakCoronet card) {
        super(card);
    }

    @Override
    public DaybreakCoronet copy() {
        return new DaybreakCoronet(this);
    }
}

class AuraAttachedPredicate implements Predicate<Permanent> {
    
    private final UUID ownId;
    
    public AuraAttachedPredicate(UUID ownId) {
        this.ownId = ownId;
    }

    @Override
    public boolean apply(Permanent input, Game game) {
        List<UUID> attachments = new LinkedList();
        attachments.addAll(input.getAttachments());
        for (UUID uuid : attachments) {
            if (!uuid.equals(ownId)) {
                Permanent attachment = game.getPermanent(uuid);
                if (attachment != null
                        && attachment.hasSubtype(SubType.AURA, game)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Aura attached";
    }
}
