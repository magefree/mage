package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.token.GoblinToken;
import mage.players.Player;

/**
 *
 * @author weirddan455
 */
public final class RulikMonsWarrenChief extends CardImpl {

    public RulikMonsWarrenChief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{G}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever Rulik Mons, Warren Chief attacks, look at the top card of your library.
        // If it's a land card, you may put it onto the battlefield tapped.
        // If you didn't put a card onto the battlefield this way, create a 1/1 red Goblin creature token.
        this.addAbility(new AttacksTriggeredAbility(new RulikMonsWarrenChiefEffect()));
    }

    private RulikMonsWarrenChief(final RulikMonsWarrenChief card) {
        super(card);
    }

    @Override
    public RulikMonsWarrenChief copy() {
        return new RulikMonsWarrenChief(this);
    }
}

class RulikMonsWarrenChiefEffect extends OneShotEffect {

    public RulikMonsWarrenChiefEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "look at the top card of your library. " +
                "If it's a land card, you may put it onto the battlefield tapped. " +
                "If you didn't put a card onto the battlefield this way, create a 1/1 red Goblin creature token.";
    }

    private RulikMonsWarrenChiefEffect(final RulikMonsWarrenChiefEffect effect) {
        super(effect);
    }

    @Override
    public RulikMonsWarrenChiefEffect copy() {
        return new RulikMonsWarrenChiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean landToPlay = false;
        Card card = controller.getLibrary().getFromTop(game);
        if (card != null) {
            controller.lookAtCards("Top card of your library", card, game);
            landToPlay = card.isLand(game) && controller.chooseUse(outcome, "Put " + card.getName() + " into play tapped?", source, game)
                    && controller.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        if (!landToPlay) {
            new GoblinToken().putOntoBattlefield(1, game, source);
        }
        return true;
    }
}
