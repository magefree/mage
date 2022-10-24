
package mage.cards.s;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author daagar
 */
public final class SerrasBoon extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("White creatures");
    
    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public SerrasBoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        
        // Enchanted creature gets +1/+2 as long as it's white. Otherwise, it gets -2/-1.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostEnchantedEffect(1, 2, Duration.WhileOnBattlefield),
                new BoostEnchantedEffect(-2, -1, Duration.WhileOnBattlefield),
                new AttachedToMatchesFilterCondition(filter),
                "Enchanted creature gets +1/+2 as long as it's white. Otherwise, it gets -2/-1"));
        this.addAbility(ability);
    }

    private SerrasBoon(final SerrasBoon card) {
        super(card);
    }

    @Override
    public SerrasBoon copy() {
        return new SerrasBoon(this);
    }
}
