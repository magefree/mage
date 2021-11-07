package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.TransformIntoSourceTriggeredAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
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
public final class WerewolfRansacker extends CardImpl {

    public WerewolfRansacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Whenever this creature transforms into Werewolf Ransacker, you may destroy target artifact. If that artifact is put into a graveyard this way, Werewolf Ransacker deals 3 damage to that artifact's controller.
        Ability ability = new TransformIntoSourceTriggeredAbility(new WerewolfRansackerEffect(), true, true);
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Werewolf Ransacker.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private WerewolfRansacker(final WerewolfRansacker card) {
        super(card);
    }

    @Override
    public WerewolfRansacker copy() {
        return new WerewolfRansacker(this);
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
