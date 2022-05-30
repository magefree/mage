package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BallroomBrawlers extends CardImpl {

    public BallroomBrawlers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Whenever Ballroom Brawlers attacks, Ballroom Brawlers and up to one other target creature you control each gain your choice of first strike or lifelink until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BallroomBrawlersEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private BallroomBrawlers(final BallroomBrawlers card) {
        super(card);
    }

    @Override
    public BallroomBrawlers copy() {
        return new BallroomBrawlers(this);
    }
}

class BallroomBrawlersEffect extends OneShotEffect {

    BallroomBrawlersEffect() {
        super(Outcome.Benefit);
        staticText = "{this} and up to one other target creature you control " +
                "both gain your choice of first strike or lifelink until end of turn";
    }

    private BallroomBrawlersEffect(final BallroomBrawlersEffect effect) {
        super(effect);
    }

    @Override
    public BallroomBrawlersEffect copy() {
        return new BallroomBrawlersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        List<Permanent> permanents = new ArrayList<>();
        permanents.add(source.getSourcePermanentIfItStillExists(game));
        permanents.add(game.getPermanent(getTargetPointer().getFirst(game, source)));
        permanents.removeIf(Objects::isNull);
        if (permanents.isEmpty()) {
            return false;
        }
        Ability ability = player.chooseUse(
                outcome, "Choose first strike or lifelink", null,
                "First Strike", "Lifelink", source, game
        ) ? FirstStrikeAbility.getInstance() : LifelinkAbility.getInstance();
        game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn)
                .setTargetPointer(new FixedTargets(permanents, game)), source);
        return true;
    }
}
