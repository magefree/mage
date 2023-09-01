package mage.cards.a;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.SpiritWhiteToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Backfir3
 */
public final class Afterlife extends CardImpl {

    public Afterlife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // Destroy target creature. It can't be regenerated. Its controller puts a
        // 1/1 white Spirit creature token with flying.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new SpiritWhiteToken()));
    }

    private Afterlife(final Afterlife card) {
        super(card);
    }

    @Override
    public Afterlife copy() {
        return new Afterlife(this);
    }
}