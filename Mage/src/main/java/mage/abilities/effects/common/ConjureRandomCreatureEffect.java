package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.RandomCardUtil;

import java.util.Collections;
import java.util.Locale;

/**
 * Conjures actual cards from the supported Arena creature catalog.
 */
public class ConjureRandomCreatureEffect extends OneShotEffect {

    private final DynamicValue manaValue;
    private final Zone zone;

    public ConjureRandomCreatureEffect(DynamicValue manaValue) {
        this(manaValue, Zone.BATTLEFIELD);
    }

    public ConjureRandomCreatureEffect(DynamicValue manaValue, Zone zone) {
        super(Outcome.PutCreatureInPlay);
        this.manaValue = manaValue;
        this.zone = zone;
        staticText = "conjure a random creature card with mana value " + manaValue
                + (zone == Zone.BATTLEFIELD ? " onto the battlefield" : " into your " + zone.toString().toLowerCase(Locale.ROOT));
    }

    protected ConjureRandomCreatureEffect(final ConjureRandomCreatureEffect effect) {
        super(effect);
        this.manaValue = effect.manaValue.copy();
        this.zone = effect.zone;
    }

    @Override
    public ConjureRandomCreatureEffect copy() {
        return new ConjureRandomCreatureEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return conjure(manaValue.calculate(game, source, this), zone, game, source) != null;
    }

    /**
     * Returns the new card so callers can cloak it or add effects to its permanent.
     * A missing mana value simply produces no card.
     */
    public static Card conjure(int manaValue, Zone zone, Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return null;
        }
        Card card = RandomCardUtil.createRandomCreature(manaValue);
        if (card == null) {
            return null;
        }
        game.loadCards(Collections.singleton(card), controller.getId());
        controller.moveCards(card, zone, source, game);
        return card;
    }
}
