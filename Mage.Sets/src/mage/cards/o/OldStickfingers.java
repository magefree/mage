package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.SetPowerToughnessSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class OldStickfingers extends CardImpl {

    public OldStickfingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{B}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // When you cast this spell, reveal cards from the top of your library until you reveal X creature cards. Put all the creature cards revealed this way into your graveyard and the rest on the bottom of your library in a random order.
        this.addAbility(new CastSourceTriggeredAbility(new OldStickfingersEffect(), false));

        // Old Stickfingers' power and toughness are equal to the number of creature cards in your graveyard.
        DynamicValue value = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURE);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetPowerToughnessSourceEffect(value, Duration.EndOfGame)));
    }

    private OldStickfingers(final OldStickfingers card) {
        super(card);
    }

    @Override
    public OldStickfingers copy() {
        return new OldStickfingers(this);
    }
}

class OldStickfingersEffect extends OneShotEffect {

    public OldStickfingersEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "reveal cards from the top of your library until you reveal X creature cards." +
                "Put all the creature cards revealed this way into your graveyard and the rest on the bottom of your library in a random order.";
    }

    public OldStickfingersEffect(final mage.cards.o.OldStickfingersEffect effect) {
        super(effect);
    }

    @Override
    public mage.cards.o.OldStickfingersEffect copy() {
        return new mage.cards.o.OldStickfingersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Object obj = getValue(CastSourceTriggeredAbility.SOURCE_CAST_SPELL_ABILITY);
        if (controller != null) {
            Set<Card> creatureCards = new LinkedHashSet<>();
            Cards otherCards = new CardsImpl();
            Cards revealed = new CardsImpl();
            FilterCreatureCard filterCard = new FilterCreatureCard("creature cards");
            int numberOfCreatures = ((SpellAbility) obj).getManaCostsToPay().getX();
            if (numberOfCreatures == 0) { // no matches so nothing is revealed
                return true;
            }
            for (Card card : controller.getLibrary().getCards(game)) {
                revealed.add(card);
                if (card != null && filterCard.match(card, game)) {
                    creatureCards.add(card);
                    if (creatureCards.size() == numberOfCreatures) {
                        break;
                    }
                } else {
                    otherCards.add(card);
                }
            }
            controller.revealCards(source, revealed, game);
            controller.moveCards(creatureCards, Zone.GRAVEYARD, source, game);
            if (!otherCards.isEmpty()) {
                controller.putCardsOnBottomOfLibrary(otherCards, game, source, false);
            }
            return true;
        }
        return false;
    }
}
