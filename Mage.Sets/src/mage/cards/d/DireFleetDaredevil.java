package mage.cards.d;

import java.util.Objects;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public final class DireFleetDaredevil extends CardImpl {

    private static final FilterCard filter = new FilterCard("instant or sorcery card from an opponent's graveyard");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public DireFleetDaredevil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // When this enters the battlefield, exile target instant or sorcery card from an opponent's graveyard. You may cast that card this turn and you may spend mana as though it were mana of any color. If that card would be put into a graveyard this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DireFleetDaredevilEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(filter));
        this.addAbility(ability);
    }

    public DireFleetDaredevil(final DireFleetDaredevil card) {
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
        this.staticText = "exile target instant or sorcery card from an opponent's graveyard. You may cast that card this turn and you may spend mana as though it were mana of any color. If that card would be put into a graveyard this turn, exile it instead";
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
                        ContinuousEffect effect = new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn);
                        effect.setTargetPointer(new FixedTarget(targetCard, game));
                        game.addEffect(effect, source);
                        effect = new DireFleetDaredevilSpendAnyManaEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard, game));
                        game.addEffect(effect, source);
                        effect = new DireFleetDaredevilReplacementEffect();
                        effect.setTargetPointer(new FixedTarget(targetCard, game));
                        game.addEffect(effect, source);

                    }
                }
            }
            return true;
        }
        return false;
    }
}

class DireFleetDaredevilSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public DireFleetDaredevilSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color";
    }

    public DireFleetDaredevilSpendAnyManaEffect(final DireFleetDaredevilSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DireFleetDaredevilSpendAnyManaEffect copy() {
        return new DireFleetDaredevilSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return source.isControlledBy(affectedControllerId)
                && Objects.equals(objectId, ((FixedTarget) getTargetPointer()).getTarget())
                && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(objectId)
                && game.getState().getZone(objectId) == Zone.STACK;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
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
        UUID eventObject = event.getTargetId();
        StackObject stackObject = game.getStack().getStackObject(eventObject);
        if (stackObject != null) {
            if (stackObject instanceof Spell) {
                game.rememberLKI(stackObject.getId(), Zone.STACK, (Spell) stackObject);
            }
            if (stackObject instanceof Card
                    && stackObject.getSourceId().equals(((FixedTarget) getTargetPointer()).getTarget())
                    && ((FixedTarget) getTargetPointer()).getZoneChangeCounter() + 1 == game.getState().getZoneChangeCounter(stackObject.getSourceId())
                    && game.getState().getZone(stackObject.getSourceId()) == Zone.STACK) {
                ((Card) stackObject).moveToExile(null, null, source.getSourceId(), game);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.getToZone() == Zone.GRAVEYARD
                && ((ZoneChangeEvent) event).getTargetId().equals(((FixedTarget) getTargetPointer()).getTarget());
    }
}
