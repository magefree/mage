package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author North
 */
public final class LostInTheMist extends CardImpl {

    public LostInTheMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{U}");

        // Counter target spell. Return target permanent to its owner's hand.
        this.getSpellAbility().addTarget(new TargetSpell());
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent());
        this.getSpellAbility().addEffect(new LostInTheMistEffect());
    }

    private LostInTheMist(final LostInTheMist card) {
        super(card);
    }

    @Override
    public LostInTheMist copy() {
        return new LostInTheMist(this);
    }
}

class LostInTheMistEffect extends OneShotEffect {

    LostInTheMistEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "Return target permanent to its owner's hand";
    }

    private LostInTheMistEffect(final LostInTheMistEffect effect) {
        super(effect);
    }

    @Override
    public LostInTheMistEffect copy() {
        return new LostInTheMistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getTargets().get(1).getFirstTarget());
        return player != null
                && permanent != null
                && player.moveCards(permanent, Zone.HAND, source, game);
    }
}
