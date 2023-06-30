package mage.cards.a;

import java.util.UUID;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.target.TargetPermanent;

/**
 * @author TheElk801
 */
public final class AssassinsTrophy extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public AssassinsTrophy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{G}");

        // Destroy target permanent an opponent controls. Its controller may search their
        // library for a basic land card, put it onto the battlefield, then shuffle.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetControllerEffect(false));
        this.getSpellAbility().addTarget(new TargetPermanent(filter));
    }

    private AssassinsTrophy(final AssassinsTrophy card) {
        super(card);
    }

    @Override
    public AssassinsTrophy copy() {
        return new AssassinsTrophy(this);
    }
}
