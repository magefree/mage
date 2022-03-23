
package mage.cards.r;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author spjspj
 */
public final class RaziasPurification extends CardImpl {

    public RaziasPurification(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}{W}");

        // Each player chooses three permanents they control, then sacrifices the rest.
        this.getSpellAbility().addEffect(new RaziasPurificationEffect());
    }

    private RaziasPurification(final RaziasPurification card) {
        super(card);
    }

    @Override
    public RaziasPurification copy() {
        return new RaziasPurification(this);
    }
}

class RaziasPurificationEffect extends OneShotEffect {

    public RaziasPurificationEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player chooses three permanents they control, then sacrifices the rest";
    }

    public RaziasPurificationEffect(RaziasPurificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<>();

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, new FilterControlledPermanent(), true);

            if (player != null && target1.canChoose(player.getId(), source, game)) {
                int chosenPermanents = 0;
                while (player.canRespond() && !target1.isChosen() && target1.canChoose(player.getId(), source, game) && chosenPermanents < 3) {
                    player.chooseTarget(Outcome.Benefit, target1, source, game);
                    for (UUID targetId : target1.getTargets()) {
                        Permanent p = game.getPermanent(targetId);
                        if (p != null) {
                            chosen.add(p);
                            chosenPermanents++;
                        }
                    }
                    target1.clearChosen();
                }
            }
        }

        for (Permanent permanent : game.getBattlefield().getAllActivePermanents()) {
            if (!chosen.contains(permanent)) {
                permanent.sacrifice(source, game);
            }
        }
        return true;
    }

    @Override
    public RaziasPurificationEffect copy() {
        return new RaziasPurificationEffect(this);
    }
}
