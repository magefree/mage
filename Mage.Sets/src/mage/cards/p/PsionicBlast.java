package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Loki
 */
public final class PsionicBlast extends CardImpl {

    public PsionicBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");


        // Psionic Blast deals 4 damage to any target and 2 damage to you.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new PsionicBlastEffect());
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private PsionicBlast(final PsionicBlast card) {
        super(card);
    }

    @Override
    public PsionicBlast copy() {
        return new PsionicBlast(this);
    }
}

class PsionicBlastEffect extends OneShotEffect {
    PsionicBlastEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 2 damage to you";
    }

    PsionicBlastEffect(final PsionicBlastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.damage(2, source.getSourceId(), source, game);
            return true;
        }
        return false;
    }

    @Override
    public PsionicBlastEffect copy() {
        return new PsionicBlastEffect(this);
    }
}