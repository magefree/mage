
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ExileCardsFromTopOfLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.FaceDownPredicate;
import mage.filter.predicate.card.OwnerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class OblivionSower extends CardImpl {

    public OblivionSower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}");
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(5);
        this.toughness = new MageInt(8);

        // When you cast Oblivion Sower, target opponent exiles the top four cards of their library, then you may put any number of land cards that player owns from exile onto the battlefield under your control.
        Ability ability = new CastSourceTriggeredAbility(new ExileCardsFromTopOfLibraryTargetEffect(4, "target opponent"), false);
        ability.addEffect(new OblivionSowerEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private OblivionSower(final OblivionSower card) {
        super(card);
    }

    @Override
    public OblivionSower copy() {
        return new OblivionSower(this);
    }
}

class OblivionSowerEffect extends OneShotEffect {

    public OblivionSowerEffect() {
        super(Outcome.PutLandInPlay);
        this.staticText = ", then you may put any number of land cards that player owns from exile onto the battlefield under your control";
    }

    public OblivionSowerEffect(final OblivionSowerEffect effect) {
        super(effect);
    }

    @Override
    public OblivionSowerEffect copy() {
        return new OblivionSowerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        /*
        8/25/2015: Oblivion Sower's ability allows you to put any land cards the player owns from exile onto the battlefield, regardless of how those cards were exiled.
        8/25/2015: Cards that are face down in exile have no characteristics. Such cards can't be put onto the battlefield with Oblivion Sower's ability.
         */
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            FilterLandCard filter = new FilterLandCard();
            filter.add(new OwnerIdPredicate(targetPlayer.getId()));
            filter.add(Predicates.not(FaceDownPredicate.instance));
            Cards exiledCards = new CardsImpl();
            exiledCards.addAll(game.getExile().getAllCards(game));
            Cards exiledLands = new CardsImpl();
            exiledLands.addAll(exiledCards.getCards(filter, controller.getId(), source, game));
            if (!exiledLands.isEmpty() && controller.chooseUse(outcome, "Put lands into play?", source, game)) {
                FilterCard filterToPlay = new FilterCard("land"
                        + (exiledLands.size() > 1 ? "s" : "") + " from exile owned by "
                        + targetPlayer.getName() + " to put into play under your control");
                TargetCard targetCards = new TargetCard(0, exiledLands.size(), Zone.EXILED, filterToPlay);
                if (controller.chooseTarget(outcome, exiledLands, targetCards, source, game)) {
                    controller.moveCards(new CardsImpl(targetCards.getTargets()), Zone.BATTLEFIELD, source, game);
                }
            }
            return true;
        }
        return false;
    }
}
