
package mage.cards.l;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author LoneFox
 */
public final class LightningCloud extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a red spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.RED));
    }

    public LightningCloud(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Whenever a player casts a red spell, you may pay {R}. If you do, Lightning Cloud deals 1 damage to any target.
        Ability ability = new SpellCastAllTriggeredAbility(new DoIfCostPaid(new DamageTargetEffect(1), new ManaCostsImpl<>("{R}")), filter, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private LightningCloud(final LightningCloud card) {
        super(card);
    }

    @Override
    public LightningCloud copy() {
        return new LightningCloud(this);
    }
}
