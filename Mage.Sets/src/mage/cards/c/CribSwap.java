
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.CribSwapShapeshifterWhiteToken;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

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
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new CribSwapEffect());

    }

    private CribSwap(final CribSwap card) {
        super(card);
    }

    @Override
    public CribSwap copy() {
        return new CribSwap(this);
    }
}

class CribSwapEffect extends OneShotEffect {

    public CribSwapEffect() {
        super(Outcome.Benefit);
        this.staticText = "Its controller creates a 1/1 colorless Shapeshifter creature token with changeling";
    }

    public CribSwapEffect(final CribSwapEffect effect) {
        super(effect);
    }

    @Override
    public CribSwapEffect copy() {
        return new CribSwapEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent targetCreature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
            if (targetCreature != null) {
                CribSwapShapeshifterWhiteToken token = new CribSwapShapeshifterWhiteToken();
                return token.putOntoBattlefield(1, game, source, targetCreature.getControllerId());
            }
        }
        return false;
    }
}
