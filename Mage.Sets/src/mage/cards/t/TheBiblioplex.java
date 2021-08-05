package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheBiblioplex extends CardImpl {

    public TheBiblioplex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {2}, {T}: Look at the top card of your library. If it's an instant or sorcery card, you may reveal it and put it into your hand. If you don't put the card into your hand, you may put it into your graveyard. Activate only if you have exactly zero or seven cards in hand.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new TheBiblioplexEffect(),
                new GenericManaCost(2), TheBiblioplexCondition.instance
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheBiblioplex(final TheBiblioplex card) {
        super(card);
    }

    @Override
    public TheBiblioplex copy() {
        return new TheBiblioplex(this);
    }
}

enum TheBiblioplexCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && (player.getHand().size() == 0 || player.getHand().size() == 7);
    }

    @Override
    public String toString() {
        return "if you have exactly zero or seven cards in hand";
    }
}

class TheBiblioplexEffect extends OneShotEffect {

    TheBiblioplexEffect() {
        super(Outcome.Benefit);
        staticText = "look at the top card of your library. If it's an instant or sorcery card, " +
                "you may reveal it and put it into your hand. If you don't put the card into your hand, " +
                "you may put it into your graveyard";
    }

    private TheBiblioplexEffect(final TheBiblioplexEffect effect) {
        super(effect);
    }

    @Override
    public TheBiblioplexEffect copy() {
        return new TheBiblioplexEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return false;
        }
        player.lookAtCards("Top of library", card, game);
        if (card.isInstantOrSorcery(game) && player.chooseUse(
                Outcome.DrawCard, "Reveal that card and put it into your hand?", source, game
        )) {
            player.revealCards(source, new CardsImpl(card), game);
            player.moveCards(card, Zone.HAND, source, game);
        } else if (player.chooseUse(
                Outcome.Discard, "Put that card into your graveyard?", source, game
        )) {
            player.moveCards(card, Zone.GRAVEYARD, source, game);
        }
        return true;
    }
}
