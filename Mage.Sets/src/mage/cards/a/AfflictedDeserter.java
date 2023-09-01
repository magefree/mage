package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.common.WerewolfFrontTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author BetaSteward
 */
public final class AfflictedDeserter extends TransformingDoubleFacedCard {

    public AfflictedDeserter(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WEREWOLF}, "{3}{R}",
                "Werewolf Ransacker",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF}, "R"
        );
        this.getLeftHalfCard().setPT(3, 2);
        this.getRightHalfCard().setPT(5, 4);

        // At the beginning of each upkeep, if no spells were cast last turn, transform Afflicted Deserter.
        this.getLeftHalfCard().addAbility(new WerewolfFrontTriggeredAbility());

        // Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.
        Ability ability = new TransformIntoSourceTriggeredAbility(
                new WerewolfRansackerEffect(), true, true
        );
        ability.addTarget(new TargetArtifactPermanent());
        this.getRightHalfCard().addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf Ransacker.
        this.getRightHalfCard().addAbility(new WerewolfBackTriggeredAbility());
    }

    private AfflictedDeserter(final AfflictedDeserter card) {
        super(card);
    }

    @Override
    public AfflictedDeserter copy() {
        return new AfflictedDeserter(this);
    }
}

class WerewolfRansackerEffect extends OneShotEffect {

    WerewolfRansackerEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy target artifact. If that artifact is put into a graveyard this way, " +
                "{this} deals 3 damage to that artifact's controller";
    }

    private WerewolfRansackerEffect(final WerewolfRansackerEffect effect) {
        super(effect);
    }

    @Override
    public WerewolfRansackerEffect copy() {
        return new WerewolfRansackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getControllerId());
        permanent.destroy(source, game, false);
        if (game.getState().getZone(permanent.getId()) != Zone.GRAVEYARD || player == null) {
            return true;
        }
        player.damage(3, source.getSourceId(), source, game);
        return true;
    }
}
