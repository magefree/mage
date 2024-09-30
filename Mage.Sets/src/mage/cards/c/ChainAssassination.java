package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FreerunningAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChainAssassination extends CardImpl {

    public ChainAssassination(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{B}");

        // Freerunning {1}{B}
        this.addAbility(new FreerunningAbility("{1}{B}"));

        // Destroy target creature. If another creature died this turn, draw a card.
        this.getSpellAbility().addEffect(new ChainAssassinationEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ChainAssassination(final ChainAssassination card) {
        super(card);
    }

    @Override
    public ChainAssassination copy() {
        return new ChainAssassination(this);
    }
}

class ChainAssassinationEffect extends OneShotEffect {

    ChainAssassinationEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target creature. If another creature died this turn, draw a card";
    }

    private ChainAssassinationEffect(final ChainAssassinationEffect effect) {
        super(effect);
    }

    @Override
    public ChainAssassinationEffect copy() {
        return new ChainAssassinationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        boolean flag = MorbidCondition.instance.apply(game, source);
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        permanent.destroy(source, game);
        if (!flag) {
            return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(1, source, game);
        }
        return true;
    }
}
