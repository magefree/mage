package mage.cards.w;

import java.util.List;
import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.PhyrexianMiteToken;
import mage.game.permanent.token.Token;

/**
 * @author TheElk801
 */
public final class WhiteSunsTwilight extends CardImpl {

    public WhiteSunsTwilight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{W}{W}");

        // You gain X life. Create X 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 and "This creature can't block." If X is 5 or more, destroy all other creatures.
        this.getSpellAbility().addEffect(new GainLifeEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addEffect(new WhiteSunsTwilightEffect());
    }

    private WhiteSunsTwilight(final WhiteSunsTwilight card) {
        super(card);
    }

    @Override
    public WhiteSunsTwilight copy() {
        return new WhiteSunsTwilight(this);
    }
}

class WhiteSunsTwilightEffect extends OneShotEffect {

    WhiteSunsTwilightEffect() {
        super(Outcome.Benefit);
        staticText = "create X 1/1 colorless Phyrexian Mite artifact creature tokens with toxic 1 " +
                "and \"This creature can't block.\" If X is 5 or more, destroy all other creatures";
    }

    private WhiteSunsTwilightEffect(final WhiteSunsTwilightEffect effect) {
        super(effect);
    }

    @Override
    public WhiteSunsTwilightEffect copy() {
        return new WhiteSunsTwilightEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = source.getManaCostsToPay().getX();
        Token token = new PhyrexianMiteToken();
        token.putOntoBattlefield(xValue, game, source);
        if (xValue < 5) {
            return true;
        }
        List<UUID> tokenIds = token.getLastAddedTokenIds();
        for (Permanent permanent : game.getBattlefield().getActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getSourceId(), game
        )) {
            if (!tokenIds.contains(permanent.getId())) {
                permanent.destroy(source, game);
            }
        }
        return true;
    }
}
