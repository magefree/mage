
package mage.cards.d;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.target.TargetPlayer;

/**
 *
 * @author noxx

 */
public final class Dreadwaters extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("lands you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public Dreadwaters(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Target player puts the top X cards of their library into their graveyard, where X is the number of lands you control.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MillCardsTargetEffect(new PermanentsOnBattlefieldCount(filter)));
    }

    private Dreadwaters(final Dreadwaters card) {
        super(card);
    }

    @Override
    public Dreadwaters copy() {
        return new Dreadwaters(this);
    }
}
