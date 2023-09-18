package mage.cards.p;

import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInPlayTargetControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PathToExile extends CardImpl {

    public PathToExile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");

        // Exile target creature. Its controller may search their library for a basic land card,
        // put that card onto the battlefield tapped, then shuffle their library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new SearchLibraryPutInPlayTargetControllerEffect(true).setText(
                "its controller may search their library for a basic land card, put that card onto the battlefield tapped, then shuffle"
        ));
    }

    private PathToExile(final PathToExile card) {
        super(card);
    }

    @Override
    public PathToExile copy() {
        return new PathToExile(this);
    }
}
