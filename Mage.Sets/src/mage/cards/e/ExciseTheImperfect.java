package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.IncubateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExciseTheImperfect extends CardImpl {

    public ExciseTheImperfect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");

        // Exile target nonland permanent. Its controller incubates X, where X is its mana value.
        this.getSpellAbility().addEffect(new ExciseTheImperfectEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private ExciseTheImperfect(final ExciseTheImperfect card) {
        super(card);
    }

    @Override
    public ExciseTheImperfect copy() {
        return new ExciseTheImperfect(this);
    }
}

class ExciseTheImperfectEffect extends OneShotEffect {

    ExciseTheImperfectEffect() {
        super(Outcome.Benefit);
        staticText = "exile target nonland permanent. Its controller incubates X, where X is its mana value";
    }

    private ExciseTheImperfectEffect(final ExciseTheImperfectEffect effect) {
        super(effect);
    }

    @Override
    public ExciseTheImperfectEffect copy() {
        return new ExciseTheImperfectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        IncubateEffect.doIncubate(permanent.getManaValue(), permanent.getControllerId(), game, source);
        return true;
    }
}
