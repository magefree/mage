package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OathOfNissa extends CardImpl {

    private static final FilterCard filter = new FilterCard("a creature, land, or planeswalker card");

    static {
        filter.add(Predicates.or(CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate(),
                CardType.PLANESWALKER.getPredicate()));
    }

    public OathOfNissa(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        addSuperType(SuperType.LEGENDARY);

        // When Oath of Nissa enters the battlefield, look at the top three cards of your library.
        // You may reveal a creature, land, or planeswalker card from among them and put it into your hand.
        // Put the rest on the bottom of your library in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryAndPickControllerEffect(
                3, 1, filter, PutCards.HAND, PutCards.BOTTOM_ANY)));

        // You may spend mana as though it were mana of any color to cast planeswalker spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OathOfNissaSpendAnyManaEffect()));
    }

    private OathOfNissa(final OathOfNissa card) {
        super(card);
    }

    @Override
    public OathOfNissa copy() {
        return new OathOfNissa(this);
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
        objectId = CardUtil.getMainCardId(game, objectId); // for split cards
        if (source.isControlledBy(affectedControllerId)) {
            MageObject mageObject = game.getObject(objectId);
            return mageObject != null && mageObject.isPlaneswalker(game);
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
