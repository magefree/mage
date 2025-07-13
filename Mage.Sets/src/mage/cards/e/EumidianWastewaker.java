package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CanBeSacrificedPredicate;
import mage.game.Game;
import mage.game.Ownerable;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetDiscard;

import java.util.*;

/**
 * @author TheElk801
 */
public final class EumidianWastewaker extends CardImpl {

    public EumidianWastewaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever this creature attacks, you and defending player each discard a card or sacrifice a permanent. You draw a card for each land card put into a graveyard this way.
        this.addAbility(new AttacksTriggeredAbility(
                new EumidianWastewakerEffect(), false, null, SetTargetPointer.PLAYER
        ));

        // Encore {6}{B}{B}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{6}{B}{B}")));
    }

    private EumidianWastewaker(final EumidianWastewaker card) {
        super(card);
    }

    @Override
    public EumidianWastewaker copy() {
        return new EumidianWastewaker(this);
    }
}

class EumidianWastewakerEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent();

    static {
        filter.add(CanBeSacrificedPredicate.instance);
    }

    EumidianWastewakerEffect() {
        super(Outcome.Benefit);
        staticText = "you and defending player each discard a card or sacrifice a permanent. " +
                "You draw a card for each land card put into a graveyard this way";
    }

    private EumidianWastewakerEffect(final EumidianWastewakerEffect effect) {
        super(effect);
    }

    @Override
    public EumidianWastewakerEffect copy() {
        return new EumidianWastewakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player defendingPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null) {
            return false;
        }
        Set<Permanent> toSacrifice = new HashSet<>();
        Set<Card> toDiscard = new HashSet<>();
        for (Player player : Arrays.asList(controller, defendingPlayer)) {
            if (player == null) {
                continue;
            }
            boolean canDiscard = !player.getHand().isEmpty();
            boolean canSacrifice = game.getBattlefield().contains(filter, player.getId(), source, game, 1);
            if (canSacrifice && !canDiscard) {
                chooseSacrifice(player, toSacrifice, game, source);
            } else if (!canSacrifice && canDiscard) {
                chooseDiscard(player, toDiscard, game, source);
            } else if (!canSacrifice && !canSacrifice) {
                continue;
            } else if (player.chooseUse(
                    Outcome.Sacrifice, "Sacrifice a permanent or discard a card?",
                    null, "Sacrifice", "Discard", source, game
            )) {
                chooseSacrifice(player, toSacrifice, game, source);
            } else {
                chooseDiscard(player, toDiscard, game, source);
            }
        }
        toSacrifice.removeIf(Objects::isNull);
        toDiscard.removeIf(Objects::isNull);
        Set<UUID> lands = new HashSet<>();
        for (Permanent permanent : toSacrifice) {
            permanent.sacrifice(source, game);
            Card card = permanent.getMainCard();
            if (card != null && card.isLand(game) && Zone.GRAVEYARD.match(game.getState().getZone(card.getId()))) {
                lands.add(card.getId());
            }
        }
        for (Card card : toDiscard) {
            Optional.ofNullable(card)
                    .map(Ownerable::getOwnerId)
                    .map(game::getPlayer)
                    .map(player -> player.discard(card, false, source, game));
            if (card.isLand(game) && Zone.GRAVEYARD.match(game.getState().getZone(card.getId()))) {
                lands.add(card.getId());
            }
        }
        Optional.of(lands)
                .map(Set::size)
                .filter(x -> x > 0)
                .ifPresent(x -> controller.drawCards(x, source, game));
        return true;
    }

    static void chooseSacrifice(Player player, Set<Permanent> toSacrifice, Game game, Ability source) {
        TargetPermanent target = new TargetControlledPermanent(filter);
        target.withChooseHint("to sacrifice");
        target.withNotTarget(true);
        player.choose(Outcome.Sacrifice, target, source, game);
        toSacrifice.add(game.getPermanent(target.getFirstTarget()));
    }

    static void chooseDiscard(Player player, Set<Card> toDiscard, Game game, Ability source) {
        TargetDiscard target = new TargetDiscard(player.getId());
        player.choose(Outcome.Discard, target, source, game);
        toDiscard.add(game.getCard(target.getFirstTarget()));
    }
}
