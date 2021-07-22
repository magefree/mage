package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WakeThePast extends CardImpl {

    public WakeThePast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{5}{R}{W}");

        // Return all artifact cards from your graveyard to the battlefield. They gain haste until end of turn.
        this.getSpellAbility().addEffect(new WakeThePastEffect());
    }

    private WakeThePast(final WakeThePast card) {
        super(card);
    }

    @Override
    public WakeThePast copy() {
        return new WakeThePast(this);
    }
}

class WakeThePastEffect extends OneShotEffect {

    WakeThePastEffect() {
        super(Outcome.Benefit);
        staticText = "return all artifact cards from your graveyard to the battlefield. " +
                "They gain haste until end of turn";
    }

    private WakeThePastEffect(final WakeThePastEffect effect) {
        super(effect);
    }

    @Override
    public WakeThePastEffect copy() {
        return new WakeThePastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT, game));
        if (cards.isEmpty()) {
            return false;
        }
        player.moveCards(cards, Zone.BATTLEFIELD, source, game);
        cards.removeIf(uuid -> game.getState().getZone(uuid) != Zone.BATTLEFIELD);
        if (cards.isEmpty()) {
            return true;
        }
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(cards, game)), source);
        return true;
    }
}
