package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.SaprolingToken;
import mage.game.permanent.token.Token;
import mage.players.Player;

import java.util.UUID;

/**
 * @author BenGiese22
 */
public final class TribuneOfRot extends CardImpl {

    public TribuneOfRot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{B/G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Tribune of Rot attacks, mill two cards. For each creature card milled this way, create a 1/1 green Saproling creature token.
        this.addAbility(new AttacksTriggeredAbility(new TribuneOfRotEffect()));
    }

    private TribuneOfRot(final TribuneOfRot card) {
        super(card);
    }

    @Override
    public TribuneOfRot copy() {
        return new TribuneOfRot(this);
    }
}

class TribuneOfRotEffect extends OneShotEffect {

    private static final Token token = new SaprolingToken();

    TribuneOfRotEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "mill two cards. For each creature card milled this way, create a 1/1 green Saproling creature token";
    }

    private TribuneOfRotEffect(final TribuneOfRotEffect effect) {
        super(effect);
    }

    @Override
    public TribuneOfRotEffect copy() {
        return new TribuneOfRotEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int numOfCreatureCardsMilled = player
                    .millCards(2, source, game)
                    .count(StaticFilters.FILTER_CARD_CREATURE, game);
            if (numOfCreatureCardsMilled > 0) {
                game.getState().processAction(game);
                token.putOntoBattlefield(numOfCreatureCardsMilled, game, source, source.getControllerId(), false, false);
            }
            return true;
        }
        return false;
    }
}
