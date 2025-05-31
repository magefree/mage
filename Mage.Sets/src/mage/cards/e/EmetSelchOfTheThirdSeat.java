package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.common.replacement.ThatSpellGraveyardExileReplacementEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.LifeLostBatchEvent;
import mage.game.events.LifeLostEvent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EmetSelchOfTheThirdSeat extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells you cast from your graveyard");

    static {
        filter.add(new CastFromZonePredicate(Zone.GRAVEYARD));
    }

    public EmetSelchOfTheThirdSeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDER);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Spells you cast from your graveyard cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 2)));

        // Whenever one or more opponents lose life, you may cast target instant or sorcery card from your graveyard. If that spell would be put into your graveyard, exile it instead. Do this only once each turn.
        this.addAbility(new EmetSelchOfTheThirdSeatAbility());
    }

    private EmetSelchOfTheThirdSeat(final EmetSelchOfTheThirdSeat card) {
        super(card);
    }

    @Override
    public EmetSelchOfTheThirdSeat copy() {
        return new EmetSelchOfTheThirdSeat(this);
    }
}

class EmetSelchOfTheThirdSeatAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<LifeLostEvent> {

    EmetSelchOfTheThirdSeatAbility() {
        super(Zone.BATTLEFIELD, new EmetSelchOfTheThirdSeatEffect());
        this.setTriggerPhrase("Whenever one or more opponents lose life, ");
        this.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.setDoOnlyOnceEachTurn(true);
    }

    private EmetSelchOfTheThirdSeatAbility(final EmetSelchOfTheThirdSeatAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LOST_LIFE_BATCH;
    }

    @Override
    public boolean checkEvent(LifeLostEvent event, Game game) {
        return game.getOpponents(getControllerId()).contains(event.getTargetId());
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        List<LifeLostEvent> filteredEvents = getFilteredEvents((LifeLostBatchEvent) event, game);
        return !filteredEvents.isEmpty()
                && CardUtil
                .getEventTargets(event)
                .stream()
                .anyMatch(uuid -> LifeLostBatchEvent.getLifeLostByPlayer(filteredEvents, uuid) > 0);
    }

    @Override
    public EmetSelchOfTheThirdSeatAbility copy() {
        return new EmetSelchOfTheThirdSeatAbility(this);
    }
}

class EmetSelchOfTheThirdSeatEffect extends OneShotEffect {

    EmetSelchOfTheThirdSeatEffect() {
        super(Outcome.Benefit);
        staticText = "cast target instant or sorcery card from your graveyard. " +
                "If that spell would be put into your graveyard, exile it instead";
    }

    private EmetSelchOfTheThirdSeatEffect(final EmetSelchOfTheThirdSeatEffect effect) {
        super(effect);
    }

    @Override
    public EmetSelchOfTheThirdSeatEffect copy() {
        return new EmetSelchOfTheThirdSeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null || !CardUtil.castSingle(player, source, game, card)) {
            // if the spell isn't cast then the ability can be used again in the same turn
            TriggeredAbility.clearDidThisTurn(source, game);
            return false;
        }
        game.addEffect(new ThatSpellGraveyardExileReplacementEffect(true)
                .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}
