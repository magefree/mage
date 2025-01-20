package mage.cards.k;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.*;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author Grath
 */
public final class KassandraEagleBearer extends CardImpl {
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(KassandraEagleBearerPredicate.instance);
    }

    public KassandraEagleBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Kassandra enters the battlefield, search your graveyard, hand, and library for a card named The Spear of Leonidas, put it onto the battlefield, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KassandraEagleBearerEffect(), false));

        // Whenever a creature you control with a legendary Equipment attached to it deals combat damage to a player, draw a card.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(new DrawCardSourceControllerEffect(1),
                filter, false, SetTargetPointer.NONE, true).setTriggerPhrase(
                        "Whenever a creature you control with a legendary Equipment attached to it deals combat damage to a player, "));
    }

    private KassandraEagleBearer(final KassandraEagleBearer card) {
        super(card);
    }

    @Override
    public KassandraEagleBearer copy() {
        return new KassandraEagleBearer(this);
    }
}

class KassandraEagleBearerEffect extends OneShotEffect {
    private static final String spearName = "The Spear of Leonidas";

    KassandraEagleBearerEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "search your graveyard, hand and/or library for a card named The Spear of Leonidas," +
                " put it onto the battlefield, then shuffle.";
    }

    private KassandraEagleBearerEffect(final KassandraEagleBearerEffect effect) {
        super(effect);
    }

    @Override
    public KassandraEagleBearerEffect copy() {
        return new KassandraEagleBearerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card spearCard = null;
        FilterCard filter = new FilterCard("card named The Spear of Leonidas");
        filter.add(new NamePredicate(spearName));
        TargetCardInLibrary libraryTarget = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(libraryTarget, source, game)) {
            for (UUID id : libraryTarget.getTargets()) {
                spearCard = game.getCard(id);
            }
        }

        // Hand is a hidden zone - you may fail to find a Spear of Leonidas that's in your hand.
        // 701.19b. If a player is searching a hidden zone for cards with a stated quality, such as a card with a
        //   certain card type or color, that player isn't required to find some or all of those cards even if they're
        //   present in that zone.
        // This is true even if your hand is revealed:
        // 400.2. ... Hidden zones are zones in which not all players can be expected to see the cards' faces.
        //   Library and hand are hidden zones, even if all the cards in one such zone happen to be revealed.
        if (spearCard == null) {
            FilterCard filter2 = new FilterCard("card from your hand named The Spear of Leonidas");
            filter2.add(new NamePredicate(spearName));
            TargetCard target = new TargetCard(0, 1, Zone.HAND, filter2);
            target.withNotTarget(true);
            Cards cards = new CardsImpl();
            cards.addAllCards(controller.getHand().getCards(filter, source.getControllerId(), source, game));
            if (!cards.isEmpty()) {
                controller.choose(outcome, cards, target, source, game);
                for (UUID id : target.getTargets()) {
                    spearCard = game.getCard(id);
                }
            }
        }

        // You cannot fail to find a spear if there is one in your graveyard, because the graveyard is not hidden.
        if (spearCard == null) {
            TargetCard target = new TargetCard(1, 1, Zone.GRAVEYARD, filter);
            target.withNotTarget(true);
            Cards cards = new CardsImpl();
            cards.addAllCards(controller.getGraveyard().getCards(filter, source.getControllerId(), source, game));
            if (!cards.isEmpty()) {
                controller.choose(outcome, cards, target, source, game);
                for (UUID id : target.getTargets()) {
                    spearCard = game.getCard(id);
                }
            }
        }

        if (spearCard != null) {
            controller.moveCards(spearCard, Zone.BATTLEFIELD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}

enum KassandraEagleBearerPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getAttachments()
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .anyMatch(attachment -> attachment.hasSubtype(SubType.EQUIPMENT, game) && attachment.isLegendary());
    }

    @Override
    public String toString() {
        return "creature you control with a legendary Equipment attached to it";
    }
}