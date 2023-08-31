
package mage.cards.c;

import mage.abilities.effects.common.CreateTokenControllerTargetPermanentEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.CribSwapShapeshifterWhiteToken;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class CribSwap extends CardImpl {

    public CribSwap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.TRIBAL, CardType.INSTANT}, "{2}{W}");
        this.subtype.add(SubType.SHAPESHIFTER);

        // Changeling
        this.addAbility(new ChangelingAbility());
        // Exile target creature. Its controller creates a 1/1 colorless Shapeshifter creature token with changeling.
        this.getSpellAbility().addEffect(new ExileTargetEffect());
        this.getSpellAbility().addEffect(new CreateTokenControllerTargetPermanentEffect(new CribSwapShapeshifterWhiteToken()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

    }

    private CribSwap(final CribSwap card) {
        super(card);
    }

    @Override
    public CribSwap copy() {
        return new CribSwap(this);
    }
}