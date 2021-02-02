
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author Plopman
 */
public final class PlagueWind extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures you don't control");
    static{
        filter.add(TargetController.NOT_YOU.getControllerPredicate());
    }
    
    public PlagueWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{7}{B}{B}");


        // Destroy all creatures you don't control. They can't be regenerated.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter, true));
    }

    private PlagueWind(final PlagueWind card) {
        super(card);
    }

    @Override
    public PlagueWind copy() {
        return new PlagueWind(this);
    }
}
