
package mage.cards.g;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class GazeOfJustice extends CardImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("untapped white creatures you control");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
        filter.add(TappedPredicate.UNTAPPED);
    }

    public GazeOfJustice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{W}");

        // As an additional cost to cast Gaze of Justice, tap three untapped white creatures you control.
        this.getSpellAbility().addCost(new TapTargetCost(new TargetControlledCreaturePermanent(3, 3, filter, true)));
        
        // Exile target creature.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
        // Flashback {5}{W}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl("{5}{W}")));
    }

    private GazeOfJustice(final GazeOfJustice card) {
        super(card);
    }

    @Override
    public GazeOfJustice copy() {
        return new GazeOfJustice(this);
    }
}
