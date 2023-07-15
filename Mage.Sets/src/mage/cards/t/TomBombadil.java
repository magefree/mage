package mage.cards.t;

import java.util.Optional;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CountersOnPermanentsCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.abilities.hint.common.CountersOnPermanentsHint;

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

        // As long as there are four or more lore counters among Sagas you control, Tom
        // Bombadil has hexproof and indestructible.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HexproofAbility.getInstance()),
                condition,
                "As long as there are four or more lore counters among Sagas you control, {this} has hexproof"));
        ability.addEffect(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(IndestructibleAbility.getInstance()), condition, "and indestructible"));
        this.addAbility(ability.addHint(hint));

        // Whenever the final chapter ability of a Saga you control resolves, reveal
        // cards from the top of your library until you reveal a Saga card. Put that
        // card onto the battlefield and the rest on the bottom of your library in a
        // random order. This ability triggers only once each turn.
        this.addAbility(new TomBombadilTriggeredAbility().setTriggersOnceEachTurn(true));
    }

    private TomBombadil(final TomBombadil card) {
        super(card);
    }

    @Override
    public TomBombadil copy() {
        return new TomBombadil(this);
    }
}

class TomBombadilTriggeredAbility extends TriggeredAbilityImpl {

    public TomBombadilTriggeredAbility() {
        super(Zone.BATTLEFIELD, new TomBombadilEffect(), false);
        setTriggerPhrase("Whenever the final chapter ability of a Saga you control resolves, ");
    }

    public TomBombadilTriggeredAbility(final TomBombadilTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TomBombadilTriggeredAbility copy() {
        return new TomBombadilTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.RESOLVING_ABILITY;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        // At this point, the stack ability no longer exists, so we can only reference
        // the ability that it came from. For EventType.RESOLVING_ABILITY, targetID is
        // the ID of the original ability (on the permanent) that the resolving ability
        // came from.
        Optional<Ability> ability_opt = game.getAbility(event.getTargetId(), event.getSourceId());
        if (!ability_opt.isPresent())
            return false;

        // Make sure it was a triggered ability (needed for checking if it's a chapter
        // ability)
        Ability ability = ability_opt.get();
        if (!(ability instanceof TriggeredAbility))
            return false;

        // Make sure it was a chapter ability
        TriggeredAbility triggeredAbility = (TriggeredAbility) ability;
        if (!SagaAbility.isChapterAbility(triggeredAbility))
            return false;

        // There's a chance that the permanent that this abiltiy came from no longer
        // exists, so try and find it on the battlefield or check last known
        // information.
        // This permanent is needed to check if the resolving ability was the final
        // chapter ability on that permanent.
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        if (permanent == null
                || !permanent.isControlledBy(getControllerId())
                || !permanent.hasSubtype(SubType.SAGA, game)) {
            return false;
        }
        // Find the max chapter number from that permanent
        int maxChapter = CardUtil
                .castStream(permanent.getAbilities(game).stream(), SagaAbility.class)
                .map(SagaAbility::getMaxChapter)
                .mapToInt(SagaChapter::getNumber)
                .sum();
        // Check if the ability was the last one
        return SagaAbility.isFinalAbility(triggeredAbility, maxChapter);
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
