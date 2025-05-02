package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.Card;
import mage.cards.Cards;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.FilterCard;
import mage.filter.common.FilterInstantOrSorceryCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author Jmlundeen
 */
public final class DivinerOfMist extends CardImpl {

    public DivinerOfMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, mill four cards. You may cast an instant or sorcery spell from your graveyard with mana value 4 or less without paying its mana cost. If that spell would be put into your graveyard, exile it instead.
        Ability ability = new AttacksTriggeredAbility(new DivinerOfMistEffect());
        ability.addEffect(new DivinerOfMistReplacementEffect());
        this.addAbility(ability);
    }

    private DivinerOfMist(final DivinerOfMist card) {
        super(card);
    }

    @Override
    public DivinerOfMist copy() {
        return new DivinerOfMist(this);
    }
}

class DivinerOfMistEffect extends OneShotEffect {

    public DivinerOfMistEffect() {
        super(Outcome.PlayForFree);
        staticText = "mill four cards. You may cast an instant or sorcery spell from your graveyard with mana value 4 or less without paying its mana cost";
    }

    protected DivinerOfMistEffect(final DivinerOfMistEffect effect) {
        super(effect);
    }

    @Override
    public DivinerOfMistEffect copy() {
        return new DivinerOfMistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards milledCards = controller.millCards(4, source, game);
        boolean hasTarget = controller.getGraveyard().getCards(game)
                .stream()
                .anyMatch(card -> card.getManaValue() <= 4 && card.isInstantOrSorcery(game));
        if (!hasTarget) {
            return true;
        }
        FilterCard filter = new FilterInstantOrSorceryCard();
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 4));
        Target target = new TargetCardInYourGraveyard(filter);
        target.withNotTarget(true);
        controller.chooseTarget(Outcome.PlayForFree, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        if (card != null) {
            card = card.getMainCard();
            game.getState().setValue("DivinerOfMist", new MageObjectReference(card, game));
            return CardUtil.castSpellWithAttributesForFree(controller, source, game, card, filter);
        }
        return true;
    }
}

class DivinerOfMistReplacementEffect extends ReplacementEffectImpl {

    public DivinerOfMistReplacementEffect() {
        super(Duration.EndOfGame, Outcome.Exile);
        staticText = "If that spell would be put into your graveyard, exile it instead.";
    }

    protected DivinerOfMistReplacementEffect(final DivinerOfMistReplacementEffect effect) {
        super(effect);
    }

    @Override
    public DivinerOfMistReplacementEffect copy() {
        return new DivinerOfMistReplacementEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            MageObjectReference card = (MageObjectReference) game.getState().getValue("DivinerOfMist");
            if (card != null) {
                ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (Zone.GRAVEYARD == ((ZoneChangeEvent) event).getToZone()) {
            Card eventCard = game.getCard(event.getSourceId());
            MageObjectReference cardMor = (MageObjectReference) game.getState().getValue("DivinerOfMist");
            if (eventCard != null && cardMor != null) {
                int zcc = eventCard.getZoneChangeCounter(game);
                return eventCard.getId().equals(cardMor.getSourceId()) && zcc == (cardMor.getZoneChangeCounter() + 1);
            }
        }
        return false;
    }
}