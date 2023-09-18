
package mage.cards.r;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Zeplar1_at_googlemail.com
 */
public final class RedScarab extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("red creatures");
    private static final FilterPermanent filter2 = new FilterPermanent("red permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
        filter2.add(new ColorPredicate(ObjectColor.RED));
    }

    public RedScarab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature can't be blocked by red creatures.
        Effect effect = new CantBeBlockedByCreaturesAttachedEffect(Duration.WhileOnBattlefield, filter, AttachmentType.AURA);
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        this.addAbility(ability);
               
        // Enchanted creature gets +2/+2 as long as an opponent controls a red permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new BoostEnchantedEffect(2, 2, Duration.WhileOnBattlefield),
                new OpponentControlsPermanentCondition(filter2),
                "Enchanted creature gets +2/+2 as long as an opponent controls a red permanent")));
    }

    private RedScarab(final RedScarab card) {
        super(card);
    }

    @Override
    public RedScarab copy() {
        return new RedScarab(this);
    }
}

