package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.TwoChoiceVote;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTargets;
import mage.target.targetpointer.TargetPointer;
import mage.util.CardUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class ElrondOfTheWhiteCouncil extends CardImpl {

    public ElrondOfTheWhiteCouncil(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Secret council -- When Elrond of the White Council enters the battlefield, each player secretly votes for fellowship or aid, then those votes are revealed. For each fellowship vote, the voter chooses a creature they control. You gain control of each creature chosen this way, and they gain "This creature can't attack its owner." Then for each aid vote, put a +1/+1 counter on each creature you control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ElrondOfWhiteCouncilEffect()
        ).setAbilityWord(AbilityWord.SECRET_COUNCIL));
    }

    private ElrondOfTheWhiteCouncil(final ElrondOfTheWhiteCouncil card) {
        super(card);
    }

    @Override
    public ElrondOfTheWhiteCouncil copy() {
        return new ElrondOfTheWhiteCouncil(this);
    }
}

class ElrondOfWhiteCouncilEffect extends OneShotEffect {

    ElrondOfWhiteCouncilEffect() {
        super(Outcome.Benefit);
        staticText = "each player secretly votes for fellowship or aid, then those votes are revealed. "
                + "For each fellowship vote, the voter chooses a creature they control. You gain control "
                + "of each creature chosen this way, and they gain \"This creature can't attack its owner.\" "
                + "Then for each aid vote, put a +1/+1 counter on each creature you control.";
    }

    private ElrondOfWhiteCouncilEffect(final ElrondOfWhiteCouncilEffect effect) {
        super(effect);
    }

    @Override
    public ElrondOfWhiteCouncilEffect copy() {
        return new ElrondOfWhiteCouncilEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        TwoChoiceVote vote = new TwoChoiceVote(
                "Fellowship",
                "Aid",
                Outcome.Benefit,
                true
        );
        vote.doVotes(source, game);


        Set<Permanent> chosenCreatures = new HashSet<>();

        // For each fellowship vote,
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            int countFellowship = (int) vote.getVotes(playerId).stream().filter(b -> b).count();
            if (countFellowship < 1) {
                continue;
            }

            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            }

            // Relevant ruling, why this is a loop and the same creature may be chosen multiple time:
            // (2023-06-16): If you have multiple votes, you can vote for fellowship multiple times. If you do, you can choose the same creature each time.
            for (int i = 0; i < countFellowship; ++i) {
                TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent(1);
                target.setNotTarget(true);

                if (player.chooseTarget(Outcome.Detriment, target, source, game)) {
                    Permanent chosen = game.getPermanent(target.getFirstTarget());
                    if (chosen == null) {
                        continue;
                    }

                    // other players must know what choice was made before them.
                    game.informPlayers(player.getLogName() + " has chosen " + chosen.getLogName() + CardUtil.getSourceLogName(game, source));
                    chosenCreatures.add(chosen);
                }
            }
        }

        // You gain control of each creature chosen this way, and they gain "This creature can't attack its owner."
        TargetPointer pointer = new FixedTargets(chosenCreatures.stream().collect(Collectors.toList()), game);

        game.addEffect(new GainControlTargetEffect(
                Duration.WhileOnBattlefield
        ).setTargetPointer(pointer), source);

        game.addEffect(new GainAbilityTargetEffect(
                new SimpleStaticAbility(new CantAttackItsOwnerEffect()),
                Duration.WhileOnBattlefield
        ).setTargetPointer(pointer), source);

        // Need to process the control change.
        game.getState().processAction(game);

        // Then for each aid vote, put a +1/+1 counter on each creature you control.
        int countAid = vote.getVoteCount(false);
        if (countAid > 0) {
            new AddCountersAllEffect(
                    CounterType.P1P1.createInstance(countAid),
                    StaticFilters.FILTER_CONTROLLED_CREATURE
            ).apply(game, source);
        }

        return true;
    }
}

class CantAttackItsOwnerEffect extends RestrictionEffect {

    CantAttackItsOwnerEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "This creature can't attack its owner";
    }

    private CantAttackItsOwnerEffect(final CantAttackItsOwnerEffect effect) {
        super(effect);
    }

    @Override
    public CantAttackItsOwnerEffect copy() {
        return new CantAttackItsOwnerEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canAttack(Permanent attacker, UUID defenderId, Ability source, Game game, boolean canUseChooseDialogs) {
        if (defenderId == null || attacker == null) {
            return true;
        }
        return !defenderId.equals(attacker.getOwnerId());
    }

}
