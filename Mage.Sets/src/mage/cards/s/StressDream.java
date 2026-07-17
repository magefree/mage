package mage.cards.s;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StressDream extends CardImpl {

    public StressDream(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}");

        // Stress Dream deals 5 damage to up to one target creature. Look at the top two cards of your library. Put one of those cards into your hand and the other on the bottom of your library.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1));
        this.getSpellAbility().addEffect(new LookLibraryAndPickControllerEffect(
                2, 1, PutCards.HAND, PutCards.BOTTOM_ANY
        ));
    }

    private StressDream(final StressDream card) {
        super(card);
    }

    @Override
    public StressDream copy() {
        return new StressDream(this);
    }
}
