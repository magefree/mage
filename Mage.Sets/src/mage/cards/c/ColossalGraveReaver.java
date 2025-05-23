package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.targetpointer.FixedTargets;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Grath
 */
public final class ColossalGraveReaver extends CardImpl {

    public ColossalGraveReaver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{B}{G}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(7);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature enters or attacks, mill three cards.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(3)));

        // Whenever one or more creature cards are put into your graveyard from your library, put one of them onto the battlefield.
        this.addAbility(new ColossalGraveReaverTriggeredAbility());
    }

    private ColossalGraveReaver(final ColossalGraveReaver card) {
        super(card);
    }

    @Override
    public ColossalGraveReaver copy() {
        return new ColossalGraveReaver(this);
    }
}

class ColossalGraveReaverEffect extends OneShotEffect {

    private static final FilterCard defaultFilter = new FilterCard("creature to return to battlefield");

    public ColossalGraveReaverEffect() {
        super(Outcome.PutCreatureInPlay);
        staticText = "put one of them onto the battlefield";
    }

    protected ColossalGraveReaverEffect(final ColossalGraveReaverEffect effect) {
        super(effect);
    }

    @Override
    public ColossalGraveReaverEffect copy() {
        return new ColossalGraveReaverEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cardsToChooseFrom = new CardsImpl();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Card card = game.getCard(targetId);
                if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                    cardsToChooseFrom.add(card);
                }
            }
            Set<Card> cardsToMove;
            switch (cardsToChooseFrom.size()) {
                case 0:
                    return false;
                case 1:
                    cardsToMove = new HashSet<>(cardsToChooseFrom.getCards(game));
                    break;
                default:
                    cardsToMove = new HashSet<>();
                    TargetCard target = new TargetCard(1, 1, Zone.ALL, defaultFilter);
                    target.withNotTarget(true);
                    controller.choose(Outcome.PlayForFree, cardsToChooseFrom, target, source, game);
                    cardsToMove.add(cardsToChooseFrom.get(target.getFirstTarget(), game));

            }
            controller.moveCards(cardsToMove, Zone.BATTLEFIELD, source, game, false, false, false, null);
            return true;
        }
        return false;
    }
}

class ColossalGraveReaverTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    ColossalGraveReaverTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ColossalGraveReaverEffect());
    }

    private ColossalGraveReaverTriggeredAbility(final ColossalGraveReaverTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ColossalGraveReaverTriggeredAbility copy() {
        return new ColossalGraveReaverTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (event.getFromZone() != Zone.LIBRARY || event.getToZone() != Zone.GRAVEYARD) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        return card != null && card.isCreature(game) && card.isOwnedBy(getControllerId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Set<Card> set = getFilteredEvents((ZoneChangeBatchEvent) event, game)
                .stream()
                .map(ZoneChangeEvent::getTargetId)
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (set.isEmpty()) {
            return false;
        }
        this.getEffects().setTargetPointer(new FixedTargets(set, game));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever one or more creature cards are put into your graveyard " +
                "from your library, put one of them onto the battlefield.";
    }
}
