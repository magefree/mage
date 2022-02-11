package mage.cards.v;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North, Loki
 */
public final class Vendetta extends CardImpl {

    public Vendetta(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{B}");

        // Destroy target nonblack creature. It can't be regenerated. You lose life equal to that creature's toughness.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_PERMANENT_CREATURE_NON_BLACK));
        this.getSpellAbility().addEffect(new DestroyTargetEffect(true));
        this.getSpellAbility().addEffect(new VendettaEffect());
    }

    private Vendetta(final Vendetta card) {
        super(card);
    }

    @Override
    public Vendetta copy() {
        return new Vendetta(this);
    }
}

class VendettaEffect extends OneShotEffect {

    public VendettaEffect() {
        super(Outcome.Damage);
        staticText = "You lose life equal to that creature's toughness";
    }

    public VendettaEffect(final VendettaEffect effect) {
        super(effect);
    }

    @Override
    public VendettaEffect copy() {
        return new VendettaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent target = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        if (player != null && target != null) {
            player.loseLife(target.getToughness().getValue(), game, source, false);
            return true;
        }
        return false;
    }

}
