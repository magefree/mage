package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author sinsedrix
 */
public final class BuildersBane extends CardImpl {

    public BuildersBane(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}");

        // Destroy X target artifacts. Builder's Bane deals damage to each player equal to the number of artifacts they controlled put into a graveyard this way.
        this.getSpellAbility().addTarget(new TargetArtifactPermanent());
        this.getSpellAbility().addEffect(new BuildersBaneEffect());
        this.getSpellAbility().setTargetAdjuster(BuildersBaneAdjuster.instance);
    }

    private BuildersBane(final BuildersBane card) {
        super(card);
    }

    @Override
    public BuildersBane copy() {
        return new BuildersBane(this);
    }
}

enum BuildersBaneAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        int xValue = ability.getManaCostsToPay().getX();
        ability.addTarget(new TargetArtifactPermanent(xValue, xValue));
    }
}

class BuildersBaneEffect extends OneShotEffect {

    public BuildersBaneEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "Destroy X target artifacts. {this} deals damage to each player equal to the number of artifacts they controlled that were put into a graveyard this way";
    }

    private BuildersBaneEffect(final BuildersBaneEffect effect) {
        super(effect);
    }

    @Override
    public BuildersBaneEffect copy() {
        return new BuildersBaneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Map<UUID, Integer> destroyedArtifactPerPlayer = new HashMap<>();

        // Destroy X target artifacts.
        for (UUID targetID : this.targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(targetID);
            if (permanent != null) {
                if (permanent.destroy(source, game, false)) {
                    game.getState().processAction(game);
                    if (permanent.getZoneChangeCounter(game) + 1 == game.getState().getZoneChangeCounter(permanent.getId())
                            && game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD) {
                        // A replacement effect has moved the card to another zone as grvayard
                        continue;
                    }
                    destroyedArtifactPerPlayer.merge(permanent.getControllerId(), 1, Integer::sum);
                }
            }
        }

        // Builder's Bane deals damage to each player equal to the number of artifacts they controlled put into a graveyard this way.
        for (Map.Entry<UUID, Integer> entry : destroyedArtifactPerPlayer.entrySet()) {
            Player player = game.getPlayer(entry.getKey());
            if (player != null) {
                player.damage(entry.getValue(), source.getSourceId(), source, game);
            }
        }

        return true;
    }
}
