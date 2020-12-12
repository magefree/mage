package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.MustBeBlockedByAllSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NessianBoar extends CardImpl {

    public NessianBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(10);
        this.toughness = new MageInt(6);

        // All creatures able to block Nessian Boar do so.
        this.addAbility(new SimpleStaticAbility(new MustBeBlockedByAllSourceEffect()));

        // Whenever Nessian Boar becomes blocked by a creature, that creature's controller draws a card.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new NessianBoarEffect(), false));
    }

    private NessianBoar(final NessianBoar card) {
        super(card);
    }

    @Override
    public NessianBoar copy() {
        return new NessianBoar(this);
    }
}

class NessianBoarEffect extends OneShotEffect {

    NessianBoarEffect() {
        super(Outcome.Benefit);
        staticText = "that creature's controller draws a card";
    }

    private NessianBoarEffect(final NessianBoarEffect effect) {
        super(effect);
    }

    @Override
    public NessianBoarEffect copy() {
        return new NessianBoarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        return player != null && player.drawCards(1, source, game) > 0;
    }
}