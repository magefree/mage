
package mage.cards.a;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class AbzanRunemark extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("as long as you control a black or green permanent");

    static {
        filter.add(Predicates.or(new ColorPredicate(ObjectColor.GREEN), new ColorPredicate(ObjectColor.BLACK)));
    }

    public AbzanRunemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted creature gets +2/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield)));
        // Enchanted creature has vigilance as long as you control a black or green permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(new GainAbilityAttachedEffect(VigilanceAbility.getInstance(), AttachmentType.AURA),
                new PermanentsOnTheBattlefieldCondition(filter), "Enchanted creature has vigilance as long as you control a black or green permanent")));
    }

    private AbzanRunemark(final AbzanRunemark card) {
        super(card);
    }

    @Override
    public AbzanRunemark copy() {
        return new AbzanRunemark(this);
    }
}
