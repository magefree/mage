
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.other.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author TheElk801
 */
public final class EnslavedHorror extends CardImpl {

    public EnslavedHorror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Enslaved Horror enters the battlefield, each other player may return a creature card from his or her graveyard to the battlefield.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new EnslavedHorrorEffect()));
    }

    public EnslavedHorror(final EnslavedHorror card) {
        super(card);
    }

    @Override
    public EnslavedHorror copy() {
        return new EnslavedHorror(this);
    }
}

class EnslavedHorrorEffect extends OneShotEffect {

    public EnslavedHorrorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "each player may put a creature card from their graveyard onto the battlefield";
    }

    public EnslavedHorrorEffect(final EnslavedHorrorEffect effect) {
        super(effect);
    }

    @Override
    public EnslavedHorrorEffect copy() {
        return new EnslavedHorrorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
            if (playerId.equals(controller.getId())) {
                continue;
            }
            Player player = game.getPlayer(playerId);
            if (player != null) {
                FilterCreatureCard filterCreatureCard = new FilterCreatureCard("creature card from your graveyard");
                filterCreatureCard.add(new OwnerIdPredicate(playerId));
                TargetCardInGraveyard target = new TargetCardInGraveyard(0, 1, filterCreatureCard);
                target.setNotTarget(true);
                if (target.canChoose(playerId, game)
                        && player.chooseTarget(outcome, target, source, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        player.moveCards(card, Zone.BATTLEFIELD, source, game);
                    }
                }
            }
        }
        return true;
    }
}
