
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LoseLifeControllerAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class CorruptedRoots extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Forest or Plains");

    static {
        filter.add(Predicates.or(
                new SubtypePredicate(SubType.FOREST),
                new SubtypePredicate(SubType.PLAINS)));
    }

    public CorruptedRoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}");
        this.subtype.add(SubType.AURA);

        // Enchant Forest or Plains
        TargetPermanent auraTarget = new TargetPermanent(filter);
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted land becomes tapped, its controller loses 2 life.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new LoseLifeControllerAttachedEffect(2), "enchanted land"));
    }

    public CorruptedRoots(final CorruptedRoots card) {
        super(card);
    }

    @Override
    public CorruptedRoots copy() {
        return new CorruptedRoots(this);
    }
}
