package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GuardianOfGhirapur extends CardImpl {

    public GuardianOfGhirapur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Guardian of Ghirapur enters the battlefield, exile up to one other target creature or artifact you control. Return it to the battlefield under its owner's control at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GuardianOfGhirapurEffect());
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_ARTIFACT_OR_CREATURE
        ));
        this.addAbility(ability);
    }

    private GuardianOfGhirapur(final GuardianOfGhirapur card) {
        super(card);
    }

    @Override
    public GuardianOfGhirapur copy() {
        return new GuardianOfGhirapur(this);
    }
}

class GuardianOfGhirapurEffect extends OneShotEffect {

    GuardianOfGhirapurEffect() {
        super(Outcome.Benefit);
        staticText = "exile up to one other target creature or artifact you control. " +
                "Return it to the battlefield under its owner's control at the beginning of the next end step";
    }

    private GuardianOfGhirapurEffect(final GuardianOfGhirapurEffect effect) {
        super(effect);
    }

    @Override
    public GuardianOfGhirapurEffect copy() {
        return new GuardianOfGhirapurEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        player.moveCards(permanent, Zone.EXILED, source, game);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false)
                        .setTargetPointer(new FixedTarget(permanent.getId(), game))
        ), source);
        return true;
    }
}
