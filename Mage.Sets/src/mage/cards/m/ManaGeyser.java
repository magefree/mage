
package mage.cards.m;

import java.util.UUID;
import mage.Mana;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.mana.DynamicManaEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;

/**
 *
 * @author Plopman
 */
public final class ManaGeyser extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("tapped land your opponents control");
    static {
        filter.add(TappedPredicate.TAPPED);
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }
    public ManaGeyser(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{R}{R}");


        // Add {R} for each tapped land your opponents control.
        this.getSpellAbility().addEffect(new DynamicManaEffect(Mana.RedMana(1), new PermanentsOnBattlefieldCount(filter)));
    }

    private ManaGeyser(final ManaGeyser card) {
        super(card);
    }

    @Override
    public ManaGeyser copy() {
        return new ManaGeyser(this);
    }
}
