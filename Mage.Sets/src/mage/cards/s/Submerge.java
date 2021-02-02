
package mage.cards.s;

import java.util.UUID;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Submerge extends CardImpl {
    
    private static final FilterPermanent filterForest = new FilterPermanent();
    private static final FilterPermanent filterIsland = new FilterPermanent();

    static {
        filterForest.add(SubType.FOREST.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
    }
    
    public Submerge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}");


        // If an opponent controls a Forest and you control an Island, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Forest and you control an Island", 
                new OpponentControlsPermanentCondition(filterForest),
                new PermanentsOnTheBattlefieldCondition(filterIsland));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));        
        // Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
                
    }

    private Submerge(final Submerge card) {
        super(card);
    }

    @Override
    public Submerge copy() {
        return new Submerge(this);
    }
}
