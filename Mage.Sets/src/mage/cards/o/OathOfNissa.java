
package mage.cards.o;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public final class OathOfNissa extends CardImpl {

    public OathOfNissa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        addSuperType(SuperType.LEGENDARY);

        // When Oath of Nissa enters the battlefield, look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new OathOfNissaEffect()));

        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathOfNissaSpendAnyManaEffect()));
    }

    public OathOfNissa(final OathOfNissa card) {
        super(card);
    }

    @Override
    public OathOfNissa copy() {
        return new OathOfNissa(this);
    }
}

class OathOfNissaEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("a creature, land, or planeswalker card");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.PLANESWALKER),
                new CardTypePredicate(CardType.LAND)));
    }

    public OathOfNissaEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top three cards of your library. You may reveal a creature, land, or planeswalker card from among them and put it into your hand. Put the rest on the bottom of your library in any order";
    }

    public OathOfNissaEffect(final OathOfNissaEffect effect) {
        super(effect);
    }

    @Override
    public OathOfNissaEffect copy() {
        return new OathOfNissaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Cards topCards = new CardsImpl();
            topCards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!topCards.isEmpty()) {
                controller.lookAtCards(sourceObject.getIdName(), topCards, game);
                int number = topCards.count(filter, source.getSourceId(), source.getControllerId(), game);
                if (number > 0) {
                    if (controller.chooseUse(outcome, "Reveal a creature, land, or planeswalker card from the looked at cards and put it into your hand?", source, game)) {
                        Card card;
                        if (number == 1) {
                            card = topCards.getCards(filter, source.getSourceId(), source.getControllerId(), game).iterator().next();
                        } else {
                            TargetCard target = new TargetCard(Zone.LIBRARY, filter);
                            controller.choose(outcome, topCards, target, game);
                            card = topCards.get(target.getFirstTarget(), game);
                        }
                        if (card != null) {
                            controller.moveCards(card, Zone.HAND, source, game);
                            controller.revealCards(sourceObject.getIdName(), new CardsImpl(card), game);
                            topCards.remove(card);
                        }
                    }
                }
                controller.putCardsOnBottomOfLibrary(topCards, game, source, true);
            }
            return true;
        }
        return false;
    }
}

class OathOfNissaSpendAnyManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public OathOfNissaSpendAnyManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "you may spend mana as though it were mana of any color to cast planeswalker spells";
    }

    public OathOfNissaSpendAnyManaEffect(final OathOfNissaSpendAnyManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public OathOfNissaSpendAnyManaEffect copy() {
        return new OathOfNissaSpendAnyManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.isControlledBy(affectedControllerId)) {
            MageObject mageObject = game.getObject(objectId);
            if (mageObject != null) {
                if (mageObject.isPlaneswalker()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
