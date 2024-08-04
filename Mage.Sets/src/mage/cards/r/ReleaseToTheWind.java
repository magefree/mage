package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetNonlandPermanent;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

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

    ReleaseToTheWindEffect() {
        super(Outcome.Benefit);
        this.staticText = "Exile target nonland permanent. For as long as that card remains exiled, its owner may cast it without paying its mana cost";
    }

    private ReleaseToTheWindEffect(final ReleaseToTheWindEffect effect) {
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
        UUID exileId = CardUtil.getExileZoneId("ReleaseToTheWind::" + targetPermanent.getOwnerId(), game);
        String exileName = "exiled by Release to the Wind";
        if (!targetPermanent.moveToExile(exileId, exileName, source, game)) {
            return false;
        }
        Card card = game.getCard(targetPermanent.getMainCard().getId());
        if (card == null || game.getState().getZone(card.getId()) != Zone.EXILED) {
            return true;
        }
        return new MayCastTargetCardEffect(Duration.Custom, CastManaAdjustment.WITHOUT_PAYING_MANA_COST, TargetController.OWNER, false)
                .setTargetPointer(new FixedTarget(card, game))
                .apply(game, source);
    }
}
