
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.SoldierToken;
import mage.players.Player;

/**
 *
 * @author Loki
 */
public final class ElspethTirel extends CardImpl {

    public ElspethTirel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{3}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELSPETH);

        this.setStartingLoyalty(4);

        this.addAbility(new LoyaltyAbility(new ElspethTirelFirstEffect(), 2));
        this.addAbility(new LoyaltyAbility(new CreateTokenEffect(new SoldierToken(), 3), -2));
        this.addAbility(new LoyaltyAbility(new ElspethTirelThirdEffect(), -5));
    }

    private ElspethTirel(final ElspethTirel card) {
        super(card);
    }

    @Override
    public ElspethTirel copy() {
        return new ElspethTirel(this);
    }
}

class ElspethTirelFirstEffect extends OneShotEffect {

    public ElspethTirelFirstEffect() {
        super(Outcome.GainLife);
        staticText = "You gain 1 life for each creature you control";
    }

    public ElspethTirelFirstEffect(final ElspethTirelFirstEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amount = game.getBattlefield().countAll(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.gainLife(amount, game, source);
        }
        return true;
    }

    @Override
    public ElspethTirelFirstEffect copy() {
        return new ElspethTirelFirstEffect(this);
    }

}

class ElspethTirelThirdEffect extends OneShotEffect {

    public ElspethTirelThirdEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Destroy all other permanents except for lands and tokens";
    }

    public ElspethTirelThirdEffect(final ElspethTirelThirdEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            if (!perm.getId().equals(source.getSourceId()) && !(perm instanceof PermanentToken) && !(perm.isLand(game))) {
                perm.destroy(source, game, false);
            }
        }
        return true;
    }

    @Override
    public ElspethTirelThirdEffect copy() {
        return new ElspethTirelThirdEffect(this);
    }

}
