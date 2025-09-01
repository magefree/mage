package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.ArtifactYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Controllable;
import mage.game.Game;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CerebralDownload extends CardImpl {

    public CerebralDownload(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{U}");

        // Surveil X, where X is the number of artifacts you control. Then draw three cards.
        this.getSpellAbility().addEffect(new CerebralDownloadEffect());
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(3).concatBy("Then"));
        this.getSpellAbility().addHint(ArtifactYouControlHint.instance);
    }

    private CerebralDownload(final CerebralDownload card) {
        super(card);
    }

    @Override
    public CerebralDownload copy() {
        return new CerebralDownload(this);
    }
}

class CerebralDownloadEffect extends OneShotEffect {

    CerebralDownloadEffect() {
        super(Outcome.Benefit);
        staticText = "surveil X, where X is the number of artifacts you control";
    }

    private CerebralDownloadEffect(final CerebralDownloadEffect effect) {
        super(effect);
    }

    @Override
    public CerebralDownloadEffect copy() {
        return new CerebralDownloadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional.ofNullable(source)
                .map(Controllable::getControllerId)
                .map(game::getPlayer)
                .filter(player -> player.surveil(
                        ArtifactYouControlCount.instance.calculate(game, source, this), source, game
                ))
                .isPresent();
    }
}
