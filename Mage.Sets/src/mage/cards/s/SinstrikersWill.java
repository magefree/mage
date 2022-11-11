
package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerCount;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author TheElk801
 */
public final class SinstrikersWill extends CardImpl {

    public SinstrikersWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has "{tap}: This creature deals damage equal to its power to target attacking or blocking creature."
        ability = new SimpleActivatedAbility(new DamageTargetEffect(new SourcePermanentPowerCount()), new TapSourceCost());
        ability.addTarget(new TargetAttackingOrBlockingCreature());
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilityAttachedEffect(
                        ability,
                        AttachmentType.AURA,
                        Duration.WhileOnBattlefield,
                        "Enchanted creature has "
                        + "\"{tap}: This creature deals damage "
                        + "equal to its power to target "
                        + "attacking or blocking creature.\""
                )
        ));
    }

    private SinstrikersWill(final SinstrikersWill card) {
        super(card);
    }

    @Override
    public SinstrikersWill copy() {
        return new SinstrikersWill(this);
    }
}
