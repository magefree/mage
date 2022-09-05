package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author ciaccona007
 */
public final class OldStickfingers extends CardImpl {

    public OldStickfingers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{B}{G}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);

        // When you cast this spell, reveal cards from the top of your library until you reveal X creature cards. Put all the creature cards revealed this way into your graveyard and the rest on the bottom of your library in a random order.
        this.addAbility(new CastSourceTriggeredAbility(new OldStickfingersEffect()));

        // Old Stickfingers' power and toughness are equal to the number of creature cards in your graveyard.
        DynamicValue value = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_CREATURES);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(value, Duration.EndOfGame)));
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
        super(Outcome.Discard);
        this.staticText = "reveal cards from the top of your library until you reveal X creature cards. Put all creature cards revealed this way into your graveyard, then put the rest on the bottom of your library in a random order";
    }

    public OldStickfingersEffect(final OldStickfingersEffect effect) {
        super(effect);
    }

    @Override
    public OldStickfingersEffect copy() {
        return new OldStickfingersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Object obj = getValue(CastSourceTriggeredAbility.SOURCE_CAST_SPELL_ABILITY);
        if (!(obj instanceof SpellAbility)) {
            return false;
        }
        int xValue = ((SpellAbility) obj).getManaCostsToPay().getX();
        if (xValue < 1) {
            return false;
        }
        Player controller = game.getPlayer(source.getControllerId());

        Cards revealed = new CardsImpl();
        Cards otherCards = new CardsImpl();
        Set<Card> creatureCards = new LinkedHashSet<>();
        for (Card card : controller.getLibrary().getCards(game)) {
            revealed.add(card);
            if (card.isCreature(game)) {
                creatureCards.add(card);
                if(creatureCards.size() == xValue) {
                    break;
                }
            } else {
                otherCards.add(card);
            }
        }
        controller.revealCards(source, revealed, game);
        controller.moveCards(creatureCards, Zone.GRAVEYARD, source, game);
        controller.putCardsOnBottomOfLibrary(otherCards, game, source, false);
        return true;
    }
}
