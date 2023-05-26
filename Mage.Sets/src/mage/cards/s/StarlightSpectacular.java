package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StarlightSpectacular extends CardImpl {

    public StarlightSpectacular(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}{W}");

        // Parade! -- At the beginning of combat on your turn, choose creatures you control one at a time until each creature you control has been chosen. Each of those creatures gets +1/+1 until end of turn for each creature chosen before it.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new StarlightSpectacularEffect(), TargetController.YOU, false
        ).withFlavorWord("Parade!"));
    }

    private StarlightSpectacular(final StarlightSpectacular card) {
        super(card);
    }

    @Override
    public StarlightSpectacular copy() {
        return new StarlightSpectacular(this);
    }
}

class StarlightSpectacularEffect extends OneShotEffect {

    StarlightSpectacularEffect() {
        super(Outcome.Benefit);
        staticText = "choose creatures you control one at a time until each creature you control has been chosen. " +
                "Each of those creatures gets +1/+1 until end of turn for each creature chosen before it. " +
                "<i>(Places everyone! The first creature in line gets +0/+0.)</i>";
    }

    private StarlightSpectacularEffect(final StarlightSpectacularEffect effect) {
        super(effect);
    }

    @Override
    public StarlightSpectacularEffect copy() {
        return new StarlightSpectacularEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int count = game.getBattlefield().count(
                StaticFilters.FILTER_CONTROLLED_CREATURE,
                source.getControllerId(), source, game
        );
        if (count < 1) {
            return false;
        }
        TargetPermanent target = new TargetControlledCreaturePermanent(count);
        target.setNotTarget(true);
        target.withChooseHint("the first creature you choose gets +0/+0");
        player.choose(outcome, target, source, game);
        int boost = 0;
        for (UUID targetId : target.getTargets()) {
            if (boost > 0) {
                game.addEffect(new BoostTargetEffect(boost, boost)
                        .setTargetPointer(new FixedTarget(targetId, game)), source);
            }
            boost++;
        }
        return true;
    }
}
