package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DireTactics extends CardImpl {

    public DireTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}{B}");

        // Exile target creature. If you don't control a Human, you lose life equal to that creature's toughness.
        this.getSpellAbility().addEffect(new DireTacticsEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private DireTactics(final DireTactics card) {
        super(card);
    }

    @Override
    public DireTactics copy() {
        return new DireTactics(this);
    }
}

class DireTacticsEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterPermanent(SubType.HUMAN, "");

    DireTacticsEffect() {
        super(Outcome.Benefit);
        staticText = "Exile target creature. If you don't control a Human, " +
                "you lose life equal to that creature's toughness.";
    }

    private DireTacticsEffect(final DireTacticsEffect effect) {
        super(effect);
    }

    @Override
    public DireTacticsEffect copy() {
        return new DireTacticsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        int toughness = permanent.getToughness().getValue();
        player.moveCards(permanent, Zone.EXILED, source, game);
        if (game.getBattlefield().countAll(filter, player.getId(), game) < 1) {
            player.loseLife(toughness, game, source, false);
        }
        return true;
    }
}