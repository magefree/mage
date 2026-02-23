package mage.cards.a;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AppealToEirdu extends CardImpl {

    public AppealToEirdu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");

        // Convoke
        this.addAbility(new ConvokeAbility());

        // One or two target creatures each get +2/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
    }

    private AppealToEirdu(final AppealToEirdu card) {
        super(card);
    }

    @Override
    public AppealToEirdu copy() {
        return new AppealToEirdu(this);
    }
}
