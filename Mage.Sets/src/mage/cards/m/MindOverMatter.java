
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public final class MindOverMatter extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact, creature, or land");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.LAND)));
    }

    public MindOverMatter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}{U}{U}{U}");


        // Discard a card: You may tap or untap target artifact, creature, or land.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MayTapOrUntapTargetEffect(), new DiscardCardCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    public MindOverMatter(final MindOverMatter card) {
        super(card);
    }

    @Override
    public MindOverMatter copy() {
        return new MindOverMatter(this);
    }
}
