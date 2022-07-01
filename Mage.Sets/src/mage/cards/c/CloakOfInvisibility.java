
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.PhasingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class CloakOfInvisibility extends CardImpl {
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by Walls");
    static {
        filter.add(Predicates.not(SubType.WALL.getPredicate()));
    }

    public CloakOfInvisibility(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);
        
        // Enchanted creature has phasing and can't be blocked except by Walls.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityAttachedEffect(PhasingAbility.getInstance(), AttachmentType.AURA).setText("Enchanted creature has phasing "));
        ability.addEffect(new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, filter, AttachmentType.AURA)
                .setText("and can’t be blocked except by Walls. " +
                        "<i>(It phases in or out before its controller untaps during each of their untap steps. " +
                        "While it’s phased out, it’s treated as though it doesn’t exist.)</i>"));
        this.addAbility(ability);
    }

    private CloakOfInvisibility(final CloakOfInvisibility card) {
        super(card);
    }

    @Override
    public CloakOfInvisibility copy() {
        return new CloakOfInvisibility(this);
    }
}
