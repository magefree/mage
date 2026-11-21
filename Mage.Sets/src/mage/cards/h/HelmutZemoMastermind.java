package mage.cards.h;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

public final class HelmutZemoMastermind extends CardImpl {

    public HelmutZemoMastermind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.NOBLE, SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new HelmutZemoMastermindAttacksAbility());
    }

    private HelmutZemoMastermind(final HelmutZemoMastermind card) {
        super(card);
    }

    @Override
    public HelmutZemoMastermind copy() {
        return new HelmutZemoMastermind(this);
    }
}

class HelmutZemoMastermindAttacksAbility extends AttacksTriggeredAbility {

    HelmutZemoMastermindAttacksAbility() {
        super(new HelmutZemoMastermindEffect());
        setTriggerPhrase("Whenever {this} attacks, you may cast target instant or sorcery card with mana value less "
                + "than or equal to its power from your graveyard. If that spell would be put into your graveyard, "
                + "exile it instead. If you cast a spell this way, put a +1/+1 counter on {this}");
    }

    private HelmutZemoMastermindAttacksAbility(final HelmutZemoMastermindAttacksAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }
        Permanent helmut = game.getPermanentOrLKIBattlefield(getSourceId());
        if (helmut == null) {
            return false;
        }
        FilterCard filter = new FilterCard("target instant or sorcery card with mana value less than or equal to "
                + helmut.getPower().getValue());
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, helmut.getPower().getValue()));
        getTargets().clear();
        addTarget(new TargetCardInYourGraveyard(filter));
        return true;
    }

    @Override
    public HelmutZemoMastermindAttacksAbility copy() {
        return new HelmutZemoMastermindAttacksAbility(this);
    }
}

class HelmutZemoMastermindEffect extends OneShotEffect {

    HelmutZemoMastermindEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast target instant or sorcery card with mana value less than or equal to {this}'s power "
                + "from your graveyard. If that spell would be put into your graveyard, exile it instead. "
                + "If you cast a spell this way, put a +1/+1 counter on {this}";
    }

    private HelmutZemoMastermindEffect(final HelmutZemoMastermindEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (controller == null || card == null
                || !controller.chooseUse(outcome, "Cast the target card without paying its mana cost?", source, game)) {
            return false;
        }
        if (!CardUtil.castSpellWithAttributesForFree(controller, source, game, card)) {
            return false;
        }

        Permanent helmut = game.getPermanent(source.getSourceId());
        if (helmut != null) {
            helmut.addCounters(CounterType.P1P1.createInstance(), source, game);
        }
        game.addEffect(new HelmutZemoMastermindExileEffect(card.getId()), source);
        return true;
    }

    @Override
    public HelmutZemoMastermindEffect copy() {
        return new HelmutZemoMastermindEffect(this);
    }
}

class HelmutZemoMastermindExileEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    HelmutZemoMastermindExileEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If that spell would be put into your graveyard, exile it instead";
    }

    private HelmutZemoMastermindExileEffect(final HelmutZemoMastermindExileEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        return cardId.equals(event.getTargetId())
                && zoneChangeEvent.getFromZone() == Zone.STACK
                && zoneChangeEvent.getToZone() == Zone.GRAVEYARD;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Card card = game.getCard(cardId);
        Player owner = card == null ? null : game.getPlayer(card.getOwnerId());
        return owner != null && owner.moveCards(card, Zone.EXILED, source, game);
    }

    @Override
    public HelmutZemoMastermindExileEffect copy() {
        return new HelmutZemoMastermindExileEffect(this);
    }
}
