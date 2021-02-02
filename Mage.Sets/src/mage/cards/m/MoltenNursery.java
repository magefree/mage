
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ColorlessPredicate;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author fireshoes
 */
public final class MoltenNursery extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a colorless spell");

    static {
        filter.add(ColorlessPredicate.instance);
    }

    public MoltenNursery(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Whenever you cast a colorless spell, Molten Nursery deals 1 damage to any target.
        Ability ability = new SpellCastControllerTriggeredAbility(new DamageTargetEffect(1), filter, false);
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

    }

    private MoltenNursery(final MoltenNursery card) {
        super(card);
    }

    @Override
    public MoltenNursery copy() {
        return new MoltenNursery(this);
    }
}
