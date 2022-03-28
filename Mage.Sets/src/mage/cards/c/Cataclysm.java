
package mage.cards.c;

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
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Cataclysm extends CardImpl {

    public Cataclysm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{W}{W}");

        // Each player chooses from the permanents they control an artifact, a creature, an enchantment, and a land, then sacrifices the rest.
        this.getSpellAbility().addEffect(new CataclysmEffect());
    }

    private Cataclysm(final Cataclysm card) {
        super(card);
    }

    @Override
    public Cataclysm copy() {
        return new Cataclysm(this);
    }
}

class CataclysmEffect extends OneShotEffect {

    public CataclysmEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "Each player chooses from among the permanents they control an artifact, a creature, an enchantment, and a land, then sacrifices the rest";
    }

    public CataclysmEffect(CataclysmEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Card> chosen = new ArrayList<>();

        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);

            Target target1 = new TargetControlledPermanent(1, 1, new FilterControlledArtifactPermanent(), true);
            Target target2 = new TargetControlledPermanent(1, 1, new FilterControlledCreaturePermanent(), true);
            Target target3 = new TargetControlledPermanent(1, 1, new FilterControlledEnchantmentPermanent(), true);
            Target target4 = new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent(), true);

            if (target1.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target1.isChosen() && target1.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target1, source, game);
                }
                Permanent artifact = game.getPermanent(target1.getFirstTarget());
                if (artifact != null) {
                    chosen.add(artifact);
                }
                target1.clearChosen();
            }

            if (target2.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target2.isChosen() && target2.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target2, source, game);
                }
                Permanent creature = game.getPermanent(target2.getFirstTarget());
                if (creature != null) {
                    chosen.add(creature);
                }
                target2.clearChosen();
            }

            if (target3.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target3.isChosen() && target3.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target3, source, game);
                }
                Permanent enchantment = game.getPermanent(target3.getFirstTarget());
                if (enchantment != null) {
                    chosen.add(enchantment);
                }
                target3.clearChosen();
            }
            
            if (target4.canChoose(player.getId(), source, game)) {
                while (player.canRespond() && !target4.isChosen() && target4.canChoose(player.getId(), source, game)) {
                    player.chooseTarget(Outcome.Benefit, target4, source, game);
                }
                Permanent land = game.getPermanent(target4.getFirstTarget());
                if (land != null) {
                    chosen.add(land);
                }
                target4.clearChosen();
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
    public CataclysmEffect copy() {
        return new CataclysmEffect(this);
    }
}
