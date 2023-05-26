package mage.cards.g;

import mage.ApprovingObject;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.CastFromHandWatcher;

import java.util.UUID;

/**
 * @author Rjayz
 */
public final class GaleWaterdeepProdigy extends CardImpl {

    public GaleWaterdeepProdigy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever you cast an instant or sorcery spell from your hand,
        // you may cast up to one of the other type from your graveyard.
        // If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.
        this.addAbility(new GaleWaterdeepProdigyTriggeredAbility());

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private GaleWaterdeepProdigy(final GaleWaterdeepProdigy card) {
        super(card);
    }

    @Override
    public GaleWaterdeepProdigy copy() {
        return new GaleWaterdeepProdigy(this);
    }
}

class GaleWaterdeepProdigyTriggeredAbility extends SpellCastControllerTriggeredAbility {

    private static final FilterCard SORCERY_FILTER = new FilterCard("a sorcery card in your graveyard");
    private static final FilterCard INSTANT_FILTER = new FilterCard("an instant card in your graveyard");

    static {
        SORCERY_FILTER.add(CardType.SORCERY.getPredicate());
        INSTANT_FILTER.add(CardType.INSTANT.getPredicate());
    }

    public GaleWaterdeepProdigyTriggeredAbility() {
        super(new GaleWaterdeepProdigyEffect(),
                new FilterInstantOrSorcerySpell("an instant or sorcery spell from your hand"),
                false);
        addWatcher(new CastFromHandWatcher());
    }

    public GaleWaterdeepProdigyTriggeredAbility(GaleWaterdeepProdigyTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!super.checkTrigger(event, game)) {
            return false;
        }

        CastFromHandWatcher watcher = game.getState().getWatcher(CastFromHandWatcher.class);
        if (watcher == null || !watcher.spellWasCastFromHand(event.getSourceId())) {
            return false;
        }

        Spell spell = game.getState().getStack().getSpell(event.getSourceId());
        if (spell == null) {
            return false;
        }

        FilterCard filterCard;
        if (spell.isSorcery()) {
            filterCard = INSTANT_FILTER;
        } else {
            filterCard = SORCERY_FILTER;
        }
        this.getTargets().clear();
        this.getTargets().add(new TargetCardInYourGraveyard(filterCard));
        return true;
    }

    @Override
    public GaleWaterdeepProdigyTriggeredAbility copy() {
        return new GaleWaterdeepProdigyTriggeredAbility(this);
    }
}

class GaleWaterdeepProdigyEffect extends OneShotEffect {

    GaleWaterdeepProdigyEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "you may cast up to one of the other type from your graveyard. " +
                "If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.";
    }

    private GaleWaterdeepProdigyEffect(final GaleWaterdeepProdigyEffect effect) {
        super(effect);
    }

    @Override
    public GaleWaterdeepProdigyEffect copy() {
        return new GaleWaterdeepProdigyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = game.getCard(this.getTargetPointer().getFirst(game, source));
        if (card != null
                && controller.chooseUse(Outcome.Neutral, "Cast " + card.getLogName() + '?', source, game)) {
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), Boolean.TRUE);
            controller.cast(controller.chooseAbilityForCast(card, game, false),
                    game, false, new ApprovingObject(source, game));
            game.getState().setValue("PlayFromNotOwnHandZone" + card.getId(), null);
            ContinuousEffect effect = new GaleWaterdeepProdigyReplacementEffect(card.getId());
            effect.setTargetPointer(new FixedTarget(card.getId(), game.getState().getZoneChangeCounter(card.getId())));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class GaleWaterdeepProdigyReplacementEffect extends ReplacementEffectImpl {

    private final UUID cardId;

    GaleWaterdeepProdigyReplacementEffect(UUID cardId) {
        super(Duration.EndOfTurn, Outcome.Exile);
        this.cardId = cardId;
        staticText = "If a spell cast from your graveyard this way would be put into your graveyard, exile it instead.";
    }

    private GaleWaterdeepProdigyReplacementEffect(final GaleWaterdeepProdigyReplacementEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
    }

    @Override
    public GaleWaterdeepProdigyReplacementEffect copy() {
        return new GaleWaterdeepProdigyReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
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
                && zEvent.getTargetId().equals(this.cardId);
    }
}
