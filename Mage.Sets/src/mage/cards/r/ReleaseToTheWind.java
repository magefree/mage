package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ReleaseToTheWind extends CardImpl {

    public ReleaseToTheWind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{U}");

        // Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost.
        getSpellAbility().addEffect(new ReleaseToTheWindEffect());
        getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    private ReleaseToTheWind(final ReleaseToTheWind card) {
        super(card);
    }

    @Override
    public ReleaseToTheWind copy() {
        return new ReleaseToTheWind(this);
    }
}

class ReleaseToTheWindEffect extends OneShotEffect {

    public ReleaseToTheWindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost";
    }

    public ReleaseToTheWindEffect(final ReleaseToTheWindEffect effect) {
        super(effect);
    }

    @Override
    public ReleaseToTheWindEffect copy() {
        return new ReleaseToTheWindEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent targetPermanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPermanent == null) {
            return false;
        }

        return PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, targetPermanent,
                TargetController.OWNER, Duration.Custom, true, false, true);
    }
}
