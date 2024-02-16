package mage.cards.v;

import mage.abilities.effects.common.CipherEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class Voidwalk extends CardImpl {

    public Voidwalk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{U}");

        // Exile target creature. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Cipher
        this.getSpellAbility().addEffect(new CipherEffect());
    }

    private Voidwalk(final Voidwalk card) {
        super(card);
    }

    @Override
    public Voidwalk copy() {
        return new Voidwalk(this);
    }
}
