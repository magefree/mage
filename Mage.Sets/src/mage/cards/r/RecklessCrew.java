package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.DwarfBerserkerToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RecklessCrew extends CardImpl {

    public RecklessCrew(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Create X 2/1 red Dwarf creature tokens, where X is the number of Vehicles you control plus the number of Equipment you control. For each of those tokens, you may attach an Equipment you control to it.
        this.getSpellAbility().addEffect(new RecklessCrewEffect());
    }

    private RecklessCrew(final RecklessCrew card) {
        super(card);
    }

    @Override
    public RecklessCrew copy() {
        return new RecklessCrew(this);
    }
}

class RecklessCrewEffect extends OneShotEffect {

    private static final FilterPermanent filter1 = new FilterControlledPermanent(SubType.EQUIPMENT);
    private static final FilterPermanent filter2 = new FilterControlledPermanent(SubType.VEHICLE);

    RecklessCrewEffect() {
        super(Outcome.Benefit);
        staticText = "create X 2/1 red Dwarf Berserker creature tokens, " +
                "where X is the number of Vehicles you control plus the number of Equipment you control. " +
                "For each of those tokens, you may attach an Equipment you control to it";
    }

    private RecklessCrewEffect(final RecklessCrewEffect effect) {
        super(effect);
    }

    @Override
    public RecklessCrewEffect copy() {
        return new RecklessCrewEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int equipCount = game.getBattlefield().count(
                filter1, source.getControllerId(), source, game
        );
        int vehicleCount = game.getBattlefield().count(
                filter2, source.getControllerId(), source, game
        );
        if (equipCount + vehicleCount < 1) {
            return false;
        }
        Token token = new DwarfBerserkerToken();
        token.putOntoBattlefield(equipCount + vehicleCount, game, source, source.getControllerId());
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        if (equipCount < 1) {
            return true;
        }
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent permanent = game.getPermanent(tokenId);
            if (permanent == null) {
                continue;
            }
            TargetPermanent target = new TargetPermanent(0, 1, filter1, true);
            target.withChooseHint("(to attach to " + permanent.getIdName() + ")");
            player.choose(outcome, target, source, game);
            permanent.addAttachment(target.getFirstTarget(), source, game);
        }
        return true;
    }
}
