package mage.cards.k;

import java.util.UUID;

import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class KitsunesTechnique extends CardImpl {

    public KitsunesTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}{U}");

        // Sneak {1}{U}
        this.addAbility(new SneakAbility(this, "{1}{U}"));

        // Target opponent mills half their library, rounded up.
        this.getSpellAbility().addEffect(new MillHalfLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private KitsunesTechnique(final KitsunesTechnique card) {
        super(card);
    }

    @Override
    public KitsunesTechnique copy() {
        return new KitsunesTechnique(this);
    }
}
