package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class DireFleetDaredevil extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public DireFleetDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When Dire Fleet Daredevil enters the battlefield, exile target instant or sorcery card from an opponent's graveyard. 
        // You may cast it this turn, and you may spend mana as though it were mana of any type to cast that spell. 
        // If that spell would be put into a graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DireFleetDaredevilEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    private DireFleetDaredevil(final DireFleetDaredevil card) {
        super(card);
    }

    @Override
    public DireFleetDaredevil copy() {
        return new DireFleetDaredevil(this);
    }
}

class DireFleetDaredevilEffect extends OneShotEffect {

    public DireFleetDaredevilEffect() {
        super(Outcome.Benefit);
        this.staticText = "exile target instant or sorcery card from an opponent's graveyard. " +
                "You may cast it this turn, and you may spend mana as though it were mana of any type " +
                "to cast that spell. If that spell would be put into a graveyard this turn, exile it instead";
    }

    public DireFleetDaredevilEffect(final DireFleetDaredevilEffect effect) {
        super(effect);
    }

    @Override
    public DireFleetDaredevilEffect copy() {
        return new DireFleetDaredevilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card targetCard = game.getCard(getTargetPointer().getFirst(game, source));
            if (targetCard != null) {
                if (controller.moveCards(targetCard, Zone.EXILED, source, game)) {
                    targetCard = game.getCard(targetCard.getId());
                    if (targetCard != null) {
                        // you may play and spend any mana
                        CardUtil.makeCardPlayable(game, source, targetCard, Duration.EndOfTurn, true);
                        // exile from graveyard
                        ContinuousEffect effect = new DireFleetDaredevilReplacementEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard, game));
                        game.addEffect(effect, source);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}

class DireFleetDaredevilReplacementEffect extends ReplacementEffectImpl {

    public DireFleetDaredevilReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "If that card would be put into a graveyard this turn, exile it instead";
    }

    public DireFleetDaredevilReplacementEffect(final DireFleetDaredevilReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DireFleetDaredevilReplacementEffect copy() {
        return new DireFleetDaredevilReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(event.getTargetId());
        Player controller = game.getPlayer(source.getControllerId());
        if (card != null && controller != null) {
            return controller.moveCards(card, Zone.EXILED, source, game);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && event.getTargetId().equals(((FixedTarget) getTargetPointer()).getTarget())
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1
                == game.getState().getZoneChangeCounter(event.getTargetId());
    }
}
