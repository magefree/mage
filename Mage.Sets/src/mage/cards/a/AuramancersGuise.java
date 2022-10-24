
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author spjspj
 */
public final class AuramancersGuise extends CardImpl {

    public AuramancersGuise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 for each Aura attached to it and has vigilance.
        DynamicValue ptBoost = new EnchantedCreatureAurasCount();
        BoostEnchantedEffect effect = new BoostEnchantedEffect(ptBoost, ptBoost, Duration.WhileOnBattlefield);
        effect.setText("Enchanted creature gets +2/+2 for each Aura attached to it");
        SimpleStaticAbility ability2 = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        ability2.addEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA).setText("and has vigilance"));
        this.addAbility(ability2);
    }

    private AuramancersGuise(final AuramancersGuise card) {
        super(card);
    }

    @Override
    public AuramancersGuise copy() {
        return new AuramancersGuise(this);
    }
}

class EnchantedCreatureAurasCount implements DynamicValue {

    public EnchantedCreatureAurasCount() {
    }

    public EnchantedCreatureAurasCount(final EnchantedCreatureAurasCount dynamicValue) {
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        int count = 0;
        Permanent aura = game.getPermanent(sourceAbility.getSourceId());
        if (aura != null) {
            Permanent permanent = game.getPermanent(aura.getAttachedTo());
            if (permanent != null) {
                List<UUID> attachments = permanent.getAttachments();
                for (UUID attachmentId : attachments) {
                    Permanent attached = game.getPermanent(attachmentId);
                    if (attached != null && attached.hasSubtype(SubType.AURA, game)) {
                        count++;
                    }

                }
                return 2 * count;
            }
        }
        return count;
    }

    @Override
    public EnchantedCreatureAurasCount copy() {
        return new EnchantedCreatureAurasCount(this);
    }

    @Override
    public String toString() {
        return "1";
    }

    @Override
    public String getMessage() {
        return "of its auras";
    }

}
