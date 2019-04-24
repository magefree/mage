
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CantAttackBlockTransformAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author halljared
 */
public final class BoundByMoonsilver extends CardImpl {

    private final static FilterControlledPermanent filter = new FilterControlledPermanent("another permanent");

    static {
        filter.add(new AnotherPredicate());
    }

    public BoundByMoonsilver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted permanent can't attack, block, or transform.
        Effect effect = new CantAttackBlockTransformAttachedEffect();
        effect.setText("Enchanted permanent can't attack, block, or transform.");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Sacrifice another permanent: Attach Bound by Moonsilver to target creature. Activate this ability only any time you could cast a sorcery and only once each turn.
        LimitedTimesPerTurnActivatedAbility limitedAbility = new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new AttachEffect(Outcome.Detriment, "Attach {this} to target creature"),
                new SacrificeTargetCost(new TargetControlledPermanent(filter)), 1);
        limitedAbility.setTiming(TimingRule.SORCERY);
        limitedAbility.addTarget(new TargetCreaturePermanent());
        this.addAbility(limitedAbility);
    }

    public BoundByMoonsilver(final BoundByMoonsilver card) {
        super(card);
    }

    @Override
    public BoundByMoonsilver copy() {
        return new BoundByMoonsilver(this);
    }
}
