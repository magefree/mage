package mage.cards.d;

import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DepartTheRealm extends CardImpl {

    public DepartTheRealm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Return target nonland permanent to its owners's hand.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());

        // Foretell {U}
        this.addAbility(new ForetellAbility(this, "{U}"));
    }

    private DepartTheRealm(final DepartTheRealm card) {
        super(card);
    }

    @Override
    public DepartTheRealm copy() {
        return new DepartTheRealm(this);
    }
}
