package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NahirisLithoforming extends CardImpl {

    public NahirisLithoforming(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{R}{R}");

        // Sacrifice X lands. For each land sacrificed this way, draw a card. You may play X additional lands this turn. Lands you control enter the battlefield tapped this turn.
        this.getSpellAbility().addEffect(new NahirisLithoformingSacrificeEffect());
        this.getSpellAbility().addEffect(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_CONTROLLED_PERMANENT_LANDS, Duration.EndOfTurn
        ));
    }

    private NahirisLithoforming(final NahirisLithoforming card) {
        super(card);
    }

    @Override
    public NahirisLithoforming copy() {
        return new NahirisLithoforming(this);
    }
}

class NahirisLithoformingSacrificeEffect extends OneShotEffect {

    NahirisLithoformingSacrificeEffect() {
        super(Outcome.Benefit);
        staticText = "Sacrifice X lands. For each land sacrificed this way, draw a card. " +
                "You may play X additional lands this turn.";
    }

    private NahirisLithoformingSacrificeEffect(final NahirisLithoformingSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public NahirisLithoformingSacrificeEffect copy() {
        return new NahirisLithoformingSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || source.getManaCostsToPay().getX() == 0) {
            return false;
        }
        int landCount = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT,
                source.getControllerId(), source, game
        );
        landCount = Math.min(source.getManaCostsToPay().getX(), landCount);
        TargetPermanent target = new TargetPermanent(
                landCount, landCount, StaticFilters.FILTER_CONTROLLED_LAND_SHORT_TEXT, true
        );
        player.choose(outcome, target, source, game);
        int counter = 0;
        for (UUID permanentId : target.getTargets()) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null && permanent.sacrifice(source, game)) {
                counter++;
            }
        }
        player.drawCards(counter, source, game);
        game.addEffect(new PlayAdditionalLandsControllerEffect(
                source.getManaCostsToPay().getX(), Duration.EndOfTurn
        ), source);
        return true;
    }
}
