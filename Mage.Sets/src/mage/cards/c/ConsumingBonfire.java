
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author Poddo
 */

public final class ConsumingBonfire extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("non-Elemental creature");
    private static final FilterPermanent filter2 = new FilterPermanent("Treefolk creature");

    static {
        filter.add(CardType.CREATURE.getPredicate());
        filter.add(Predicates.not(SubType.ELEMENTAL.getPredicate()));
        filter2.add(CardType.CREATURE.getPredicate());
        filter2.add(SubType.TREEFOLK.getPredicate());
    }

    public ConsumingBonfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.SORCERY},"{3}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        // Choose one - Consuming Bonfire deals 4 damage to target non-Elemental creature; 
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
                
        //or Consuming Bonfire deals 7 damage to target Treefolk creature.
        Mode mode = new Mode(new DamageTargetEffect(7));
        mode.addTarget(new TargetPermanent(filter2));
        this.getSpellAbility().addMode(mode);
    }

    private ConsumingBonfire(final ConsumingBonfire card) {
        super(card);
    }

    @Override
    public ConsumingBonfire copy() {
        return new ConsumingBonfire(this);
    }
}
