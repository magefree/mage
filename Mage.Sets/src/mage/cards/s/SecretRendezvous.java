package mage.cards.s;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SecretRendezvous extends CardImpl {

    public SecretRendezvous(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{W}{W}");

        // You and target opponent each draw three cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).setText("you"));
        this.getSpellAbility().addEffect(new DrawCardTargetEffect(3).setText("and target opponent each draw three cards"));
        this.getSpellAbility().addTarget(new TargetOpponent());
    }

    private SecretRendezvous(final SecretRendezvous card) {
        super(card);
    }

    @Override
    public SecretRendezvous copy() {
        return new SecretRendezvous(this);
    }
}
