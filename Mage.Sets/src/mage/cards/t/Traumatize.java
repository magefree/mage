package mage.cards.t;

import mage.abilities.effects.common.MillHalfLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class Traumatize extends CardImpl {

    public Traumatize(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}{U}");

        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MillHalfLibraryTargetEffect(false));
    }

    private Traumatize(final Traumatize card) {
        super(card);
    }

    @Override
    public Traumatize copy() {
        return new Traumatize(this);
    }
}
