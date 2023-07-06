package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HurlThroughHell extends CardImpl {

    public HurlThroughHell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}{R}");

        // Exile target creature. Until the end of your next turn, you may cast that card and you may spend mana as though it were mana of any color to cast that spell.
        this.getSpellAbility().addEffect(new HurlThroughHellEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private HurlThroughHell(final HurlThroughHell card) {
        super(card);
    }

    @Override
    public HurlThroughHell copy() {
        return new HurlThroughHell(this);
    }
}

class HurlThroughHellEffect extends OneShotEffect {

    HurlThroughHellEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature. Until the end of your next turn, you may cast that card " +
                "and you may spend mana as though it were mana of any color to cast that spell";
    }

    private HurlThroughHellEffect(final HurlThroughHellEffect effect) {
        super(effect);
    }

    @Override
    public HurlThroughHellEffect copy() {
        return new HurlThroughHellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }

        CardUtil.exileAndMakeCastable(
            game, source, permanent,
            Duration.UntilEndOfYourNextTurn,
            CardUtil.SimpleCastManaAdjustment.AS_THOUGH_ANY_MANA_COLOR,
            null
        );
        return true;
    }
}
