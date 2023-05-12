
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.StitcherGeralfZombieToken;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class StitcherGeralf extends CardImpl {

    public StitcherGeralf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // {2}{U}, {tap}: Each player puts the top three cards of their library into their graveyard. Exile up to two creature cards put into graveyards this way. Create an X/X blue Zombie creature token, where X is the total power of the cards exiled this way.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new StitcherGeralfEffect(), new ManaCostsImpl<>("{2}{U}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private StitcherGeralf(final StitcherGeralf card) {
        super(card);
    }

    @Override
    public StitcherGeralf copy() {
        return new StitcherGeralf(this);
    }
}

class StitcherGeralfEffect extends OneShotEffect {

    public StitcherGeralfEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player mills three cards. Exile up to two creature cards put into graveyards this way. Create an X/X blue Zombie creature token, where X is the total power of the cards exiled this way";
    }

    public StitcherGeralfEffect(final StitcherGeralfEffect effect) {
        super(effect);
    }

    @Override
    public StitcherGeralfEffect copy() {
        return new StitcherGeralfEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    cards.addAll(player.millCards(3,source,game));
                }
            }
            cards.removeIf(uuid -> game.getState().getZone(uuid)!=Zone.GRAVEYARD);
            TargetCard target = new TargetCard(0, 2, Zone.GRAVEYARD, new FilterCreatureCard("creature cards to exile"));
            controller.chooseTarget(outcome, cards, target, source, game);
            int power = 0;
            for (UUID cardId : target.getTargets()) {
                Card card = game.getCard(cardId);
                if (card != null) {
                    power += card.getPower().getValue();
                    controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.GRAVEYARD, true);
                }
            }
            return new CreateTokenEffect(new StitcherGeralfZombieToken(power)).apply(game, source);
        }
        return false;
    }
}
