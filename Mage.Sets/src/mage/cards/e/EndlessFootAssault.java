package mage.cards.e;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.token.Ninja11Token;

/**
 *
 * @author muz
 */
public final class EndlessFootAssault extends CardImpl {

    public EndlessFootAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");

        // Squad {1}{W}
        this.addAbility(new SquadAbility(new ManaCostsImpl<>("{1}{W}")));

        // Whenever you attack, for each opponent, create a 1/1 black Ninja creature token that's tapped and attacking that player.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new EndlessFootAssaultEffect(), 1));
    }

    private EndlessFootAssault(final EndlessFootAssault card) {
        super(card);
    }

    @Override
    public EndlessFootAssault copy() {
        return new EndlessFootAssault(this);
    }
}

class EndlessFootAssaultEffect extends OneShotEffect {

    public EndlessFootAssaultEffect() {
        super(Outcome.Benefit);
        staticText = "for each opponent, create a 1/1 black Ninja creature token that's tapped and attacking that player";
    }

    protected EndlessFootAssaultEffect(final EndlessFootAssaultEffect effect) {
        super(effect);
    }

    @Override
    public EndlessFootAssaultEffect copy() {
        return new EndlessFootAssaultEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            new Ninja11Token().putOntoBattlefield(1, game, source, source.getControllerId(), true, true, playerId);
        }
        return true;
    }
}
