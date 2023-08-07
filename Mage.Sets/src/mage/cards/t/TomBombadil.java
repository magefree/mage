package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.FinalChapterAbilityResolvesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CountersOnPermanentsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CountersOnPermanentsHint;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.*;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author alexander-novo
 */
public final class TomBombadil extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.SAGA, "Sagas you control");
    private static final CountersOnPermanentsCondition condition = new CountersOnPermanentsCondition(filter,
            CounterType.LORE, ComparisonType.MORE_THAN, 3);
    private static final CountersOnPermanentsHint hint = new CountersOnPermanentsHint(condition);

    public TomBombadil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.BARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // As long as there are four or more lore counters among Sagas you control, Tom Bombadil has hexproof and indestructible.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HexproofAbility.getInstance()),
            condition,
            "As long as there are four or more lore counters among Sagas you control, {this} has hexproof"));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), condition, "and indestructible"));
        this.addAbility(ability.addHint(hint));

        // Whenever the final chapter ability of a Saga you control resolves, reveal cards from the top of your library until you reveal a Saga card. Put that card onto the battlefield and the rest on the bottom of your library in a random order. This ability triggers only once each turn.
        this.addAbility(new FinalChapterAbilityResolvesTriggeredAbility(new TomBombadilEffect()).setTriggersOnceEachTurn(true));
    }

    private TomBombadil(final TomBombadil card) {
        super(card);
    }

    @Override
    public TomBombadil copy() {
        return new TomBombadil(this);
    }
}

// From PrismaticBridgeEffect
class TomBombadilEffect extends OneShotEffect {

    public TomBombadilEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "reveal cards from the top of your library until you reveal a Saga card. Put that card onto the battlefield and the rest on the bottom of your library in a random order";
    }

    private TomBombadilEffect(final TomBombadilEffect effect) {
        super(effect);
    }

    @Override
    public TomBombadilEffect copy() {
        return new TomBombadilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards toReveal = new CardsImpl();
        Card toBattlefield = null;
        for (Card card : controller.getLibrary().getCards(game)) {
            toReveal.add(card);
            if (card.hasSubtype(SubType.SAGA, game)) {
                toBattlefield = card;
                break;
            }
        }
        controller.revealCards(source, toReveal, game);
        if (toBattlefield != null) {
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game);
            toReveal.retainZone(Zone.LIBRARY, game);
        }
        controller.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
