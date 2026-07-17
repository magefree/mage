package mage.cards.e;

import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Erode extends CardImpl {

    public Erode(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Destroy target creature or planeswalker. Its controller may search their library for a basic land card, put it onto the battlefield tapped, then shuffle.
        this.getSpellAbility().addEffect(new DestroyTargetEffect());
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetControllerEffect(true)
                .setText("its controller may search their library for a basic land card, " +
                        "put it onto the battlefield tapped, then shuffle"));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
    }

    private Erode(final Erode card) {
        super(card);
    }

    @Override
    public Erode copy() {
        return new Erode(this);
    }
}
