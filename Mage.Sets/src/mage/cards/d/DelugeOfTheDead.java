package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DelugeOfTheDead extends CardImpl {

    public DelugeOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "");

        this.color.setBlack(true);
        this.nightCard = true;

        // When Deluge of the Dead enters the battlefield, create two 2/2 black Zombie creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ZombieToken(), 2)));

        // {2}{B}: Exile target card from a graveyard. If it was a creature card, create a 2/2 black Zombie creature token.
        Ability ability = new SimpleActivatedAbility(new DelugeOfTheDeadEffect(), new ManaCostsImpl<>("{2}{B}"));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private DelugeOfTheDead(final DelugeOfTheDead card) {
        super(card);
    }

    @Override
    public DelugeOfTheDead copy() {
        return new DelugeOfTheDead(this);
    }
}

class DelugeOfTheDeadEffect extends OneShotEffect {

    DelugeOfTheDeadEffect() {
        super(Outcome.Benefit);
        staticText = "exile target card from a graveyard. " +
                "If it was a creature card, create a 2/2 black Zombie creature token";
    }

    private DelugeOfTheDeadEffect(final DelugeOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public DelugeOfTheDeadEffect copy() {
        return new DelugeOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        if (card.isCreature(game)) {
            new ZombieToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
