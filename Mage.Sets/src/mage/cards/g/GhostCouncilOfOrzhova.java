package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldOwnerNextEndStepSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class GhostCouncilOfOrzhova extends CardImpl {

    public GhostCouncilOfOrzhova(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Ghost Council of Orzhova enters the battlefield, target opponent loses 1 life and you gain 1 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GhostCouncilOfOrzhovaEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {1}, Sacrifice a creature: Exile Ghost Council of Orzhova. Return it to the battlefield under its owner's control at the beginning of the next end step.
        ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new ExileReturnBattlefieldOwnerNextEndStepSourceEffect(true),
                new GenericManaCost(1));
        ability.addCost(new SacrificeTargetCost(
                new TargetControlledCreaturePermanent(
                        FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    public GhostCouncilOfOrzhova(final GhostCouncilOfOrzhova card) {
        super(card);
    }

    @Override
    public GhostCouncilOfOrzhova copy() {
        return new GhostCouncilOfOrzhova(this);
    }

}

class GhostCouncilOfOrzhovaEffect extends OneShotEffect {

    GhostCouncilOfOrzhovaEffect() {
        super(Outcome.GainLife);
        staticText = "target opponent loses 1 life and you gain 1 life";
    }

    GhostCouncilOfOrzhovaEffect(final GhostCouncilOfOrzhovaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null 
                && controller != null) {
            targetPlayer.loseLife(1, game, false);
            controller.gainLife(1, game, source);
            return true;
        }
        return false;
    }

    @Override
    public GhostCouncilOfOrzhovaEffect copy() {
        return new GhostCouncilOfOrzhovaEffect(this);
    }

}
