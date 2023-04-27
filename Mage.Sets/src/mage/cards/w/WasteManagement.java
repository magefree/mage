package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.RogueToken;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInASingleGraveyard;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WasteManagement extends CardImpl {

    public WasteManagement(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Kicker {3}{B}
        this.addAbility(new KickerAbility("{3}{B}"));

        // Exile up to two target cards from a single graveyard. If this spell was kicked, instead exile target player's graveyard. Create a 2/2 black Rogue creature token for each creature card exiled this way.
        this.getSpellAbility().addEffect(new WasteManagementEffect());
        this.getSpellAbility().setTargetAdjuster(WasteManagementAdjuster.instance);
    }

    private WasteManagement(final WasteManagement card) {
        super(card);
    }

    @Override
    public WasteManagement copy() {
        return new WasteManagement(this);
    }
}

enum WasteManagementAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        if (KickedCondition.ONCE.apply(game, ability)) {
            ability.addTarget(new TargetPlayer());
        } else {
            ability.addTarget(new TargetCardInASingleGraveyard(0, 2, StaticFilters.FILTER_CARD));
        }
    }
}

class WasteManagementEffect extends OneShotEffect {

    WasteManagementEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to two target cards from a single graveyard. " +
                "If this spell was kicked, instead exile target player's graveyard. " +
                "Create a 2/2 black Rogue creature token for each creature card exiled this way";
    }

    private WasteManagementEffect(final WasteManagementEffect effect) {
        super(effect);
    }

    @Override
    public WasteManagementEffect copy() {
        return new WasteManagementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        if (KickedCondition.ONCE.apply(game, source)) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null) {
                cards.addAll(player.getGraveyard());
            }
        } else {
            cards.addAll(getTargetPointer().getTargets(game, source));
        }
        if (cards.isEmpty()) {
            return false;
        }
        controller.moveCards(cards, Zone.EXILED, source, game);
        cards.retainZone(Zone.EXILED, game);
        int count = cards.count(StaticFilters.FILTER_CARD_CREATURE, game);
        if (count > 0) {
            new RogueToken().putOntoBattlefield(count, game, source);
        }
        return true;
    }
}
