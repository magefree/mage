package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;

/**
 * @author karapuzz14
 */
public class SeekCardEffect extends OneShotEffect {

    private final FilterCard filter;

    private final Zone inZone;

    private final boolean tapped;

    private final int amount;

    public SeekCardEffect(FilterCard filter) {
        this(filter, 1, Zone.HAND, false);
    }

    public SeekCardEffect(FilterCard filter, int amount) {
        this(filter, amount, Zone.HAND, false);
    }

    public SeekCardEffect(FilterCard filter, int amount, Zone inZone) {
        this(filter, amount, inZone, false);
    }

    /**
     * @param filter for selecting a card
     */
    public SeekCardEffect(FilterCard filter, int amount, Zone inZone, boolean tapped) {
        super(Outcome.Benefit);
        this.filter = filter;
        this.amount = amount;
        this.inZone = inZone;
        this.tapped = tapped;
        StringBuilder sb = new StringBuilder("seek ");
        String value = StaticValue.get(amount).toString();
        sb.append(CardUtil.numberToText(value, ""));
        if (amount > 1) {
            sb.append(" ");
        }
        sb.append(filter.getMessage());
        this.staticText = sb.toString();
    }

    private SeekCardEffect(final SeekCardEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.inZone = effect.inZone;
        this.tapped = effect.tapped;
    }

    @Override
    public SeekCardEffect copy() {
        return new SeekCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Set<Card> cards = controller.seekCard(filter, inZone, tapped, amount, source, game);

        return cards != null && !cards.isEmpty();
    }

}
