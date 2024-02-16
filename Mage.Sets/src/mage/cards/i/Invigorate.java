package mage.cards.i;

import java.util.UUID;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.GainLifeOpponentCost;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration; 
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Invigorate extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("If you control a Forest");
    
    static {
        filter.add(SubType.FOREST.getPredicate());
    }
    
    public Invigorate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{G}");

        // If you control a Forest, rather than pay this spell's mana cost, you may have an opponent gain 3 life.
        this.addAbility(new AlternativeCostSourceAbility(new GainLifeOpponentCost(3), new PermanentsOnTheBattlefieldCondition(filter),
                "If you control a Forest, rather than pay this spell's mana cost, you may have an opponent gain 3 life."));
        
        // Target creature gets +4/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(4,4,Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private Invigorate(final Invigorate card) {
        super(card);
    }

    @Override
    public Invigorate copy() {
        return new Invigorate(this);
    }
}
