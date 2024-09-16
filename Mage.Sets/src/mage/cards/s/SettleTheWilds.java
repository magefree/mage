
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author Sidorovich77
 */
public final class SettleTheWilds extends CardImpl {

    public SettleTheWilds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}");

        // Seek a basic land card and put it onto the battlefield tapped. Then seek a permanent card with mana value equal to the number of lands you control.
        this.getSpellAbility().addEffect(new SeekCardEffect(StaticFilters.FILTER_CARD_BASIC_LAND_A, 1, Zone.BATTLEFIELD, true).setText("Seek a basic land card and put it onto the battlefield tapped"));
        this.getSpellAbility().addEffect(new SettleTheWildsSeekEffect());

    }

    private SettleTheWilds(final SettleTheWilds card) {
        super(card);
    }

    @Override
    public SettleTheWilds copy() {
        return new SettleTheWilds(this);
    }
}

class SettleTheWildsSeekEffect extends OneShotEffect {

    SettleTheWildsSeekEffect() {
        super(Outcome.Benefit);
        staticText = "Then seek a permanent card with mana value equal to the number of lands you control.";
    }

    private SettleTheWildsSeekEffect(final SettleTheWildsSeekEffect effect) {
        super(effect);
    }

    @Override
    public SettleTheWildsSeekEffect copy() {
        return new SettleTheWildsSeekEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int lands = game.getBattlefield().countAll(StaticFilters.FILTER_LAND, game.getControllerId(source.getSourceId()), game);
        FilterPermanentCard filter = new FilterPermanentCard();
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, lands));
        return player.seekCard(filter, source, game);
    }
}