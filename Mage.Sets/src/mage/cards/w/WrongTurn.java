package mage.cards.w;

import mage.abilities.effects.common.TargetPlayerGainControlTargetPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WrongTurn extends CardImpl {

    public WrongTurn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Target opponent gains control of target creature.
        this.getSpellAbility().addEffect(new TargetPlayerGainControlTargetPermanentEffect());
        this.getSpellAbility().addTarget(new TargetOpponent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private WrongTurn(final WrongTurn card) {
        super(card);
    }

    @Override
    public WrongTurn copy() {
        return new WrongTurn(this);
    }
}
