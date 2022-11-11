package mage.cards.c;

import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CutYourLosses extends CardImpl {

    public CutYourLosses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{U}{U}");

        // Casualty 2
        this.addAbility(new CasualtyAbility(2));

        // Target player mills half their library, rounded down.
        this.getSpellAbility().addEffect(new MillHalfLibraryTargetEffect(false));
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private CutYourLosses(final CutYourLosses card) {
        super(card);
    }

    @Override
    public CutYourLosses copy() {
        return new CutYourLosses(this);
    }
}
