package mage.cards.d;

import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Desynchronize extends CardImpl {

    public Desynchronize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Target nonland permanent's owner puts it on the top or bottom of their library. Scry 2.
        this.getSpellAbility().addEffect(new PutOnTopOrBottomLibraryTargetEffect(
                "target nonland permanent's owner puts it on the top or bottom of their library"
        ));
        this.getSpellAbility().addEffect(new ScryEffect(2, false));
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private Desynchronize(final Desynchronize card) {
        super(card);
    }

    @Override
    public Desynchronize copy() {
        return new Desynchronize(this);
    }
}
