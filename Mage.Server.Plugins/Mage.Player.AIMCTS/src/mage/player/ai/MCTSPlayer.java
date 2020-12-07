
package mage.player.ai;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.SpellAbility;
import mage.abilities.common.PassAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.game.Game;
import mage.game.permanent.Permanent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class MCTSPlayer extends ComputerPlayer {

     private static final Logger logger = Logger.getLogger(MCTSPlayer.class);

    protected PassAbility pass = new PassAbility();

    private NextAction nextAction;

    public enum NextAction {
        PRIORITY, SELECT_ATTACKERS, SELECT_BLOCKERS
    }

    public MCTSPlayer(UUID id) {
        super(id);
        this.pass.setControllerId(id);
    }

    public MCTSPlayer(final MCTSPlayer player) {
        super(player);
        this.pass = player.pass.copy();
        this.nextAction = player.nextAction;
    }

    @Override
    public MCTSPlayer copy() {
        return new MCTSPlayer(this);
    }

    protected List<ActivatedAbility> getPlayableAbilities(Game game) {
        List<ActivatedAbility> playables = getPlayable(game, true);
        playables.add(pass);
        return playables;
    }

    public List<Ability> getPlayableOptions(Game game) {
        List<Ability> all = new ArrayList<>();
        List<ActivatedAbility> playables = getPlayableAbilities(game);
        for (ActivatedAbility ability: playables) {
            List<Ability> options = game.getPlayer(playerId).getPlayableOptions(ability, game);
            if (options.isEmpty()) {
                if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                    simulateVariableCosts(ability, all, game);
                }
                else {
                    all.add(ability);
                }
            }
            else {
                for (Ability option: options) {
                    if (!ability.getManaCosts().getVariableCosts().isEmpty()) {
                        simulateVariableCosts(option, all, game);
                    }
                    else {
                        all.add(option);
                    }
                }
            }
        }
        return all;
    }

    protected void simulateVariableCosts(Ability ability, List<Ability> options, Game game) {
        int numAvailable = getAvailableManaProducers(game).size() - ability.getManaCosts().convertedManaCost();
        int start = 0;
        if (!(ability instanceof SpellAbility)) {
            //only use x=0 on spell abilities
            if (numAvailable == 0)
                return;
            else
                start = 1;
        }
        for (int i = start; i < numAvailable; i++) {
            Ability newAbility = ability.copy();
            newAbility.getManaCostsToPay().add(new GenericManaCost(i));
            options.add(newAbility);
        }
    }

    public List<List<UUID>> getAttacks(Game game) {
        List<List<UUID>> engagements = new ArrayList<>();
        List<Permanent> attackersList = super.getAvailableAttackers(game);
        //use binary digits to calculate powerset of attackers
        int powerElements = (int) Math.pow(2, attackersList.size());
        StringBuilder binary = new StringBuilder();
        for (int i = powerElements - 1; i >= 0; i--) {
            binary.setLength(0);
            binary.append(Integer.toBinaryString(i));
            while (binary.length() < attackersList.size()) {
                binary.insert(0, '0');
            }
            List<UUID> engagement = new ArrayList<>();
            for (int j = 0; j < attackersList.size(); j++) {
                if (binary.charAt(j) == '1') {
                    engagement.add(attackersList.get(j).getId());
                }
            }
            engagements.add(engagement);
        }
        return engagements;
    }

    public List<List<List<UUID>>> getBlocks(Game game) {
        List<List<List<UUID>>> engagements = new ArrayList<>();
        int numGroups = game.getCombat().getGroups().size();
        if (numGroups == 0) {
            return engagements;
        }

        //add a node with no blockers
        List<List<UUID>> engagement = new ArrayList<>();
        for (int i = 0; i < numGroups; i++) {
            engagement.add(new ArrayList<>());
        }
        engagements.add(engagement);

        List<Permanent> blockers = getAvailableBlockers(game);
        addBlocker(game, engagement, blockers, engagements);

        return engagements;
    }

    private List<List<UUID>> copyEngagement(List<List<UUID>> engagement) {
        List<List<UUID>> newEngagement = new ArrayList<>();
        for (List<UUID> group: engagement) {
            newEngagement.add(new ArrayList<>(group));
        }
        return newEngagement;
    }

    protected void addBlocker(Game game, List<List<UUID>> engagement, List<Permanent> blockers, List<List<List<UUID>>> engagements) {
        if (blockers.isEmpty())
            return;
        int numGroups = game.getCombat().getGroups().size();
        //try to block each attacker with each potential blocker
        Permanent blocker = blockers.get(0);
//        if (logger.isDebugEnabled())
//            logger.debug("simulating -- block:" + blocker);
        List<Permanent> remaining = remove(blockers, blocker);
        for (int i = 0; i < numGroups; i++) {
            if (game.getCombat().getGroups().get(i).canBlock(blocker, game)) {
                List<List<UUID>>newEngagement = copyEngagement(engagement);
                newEngagement.get(i).add(blocker.getId());
                engagements.add(newEngagement);
//                    logger.debug("simulating -- found redundant block combination");
                addBlocker(game, newEngagement, remaining, engagements);  // and recurse minus the used blocker
            }
        }
        addBlocker(game, engagement, remaining, engagements);
    }

    public NextAction getNextAction() {
        return nextAction;
    }

    public void setNextAction(NextAction action) {
        this.nextAction = action;
    }

    @Override
    public boolean priority(Game game) {
//        logger.info("Paused for Priority for player:" + getName());
        game.pause();
        nextAction = NextAction.PRIORITY;
        return false;
    }

//    @Override
//    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Target target, UUID sourceId, Game game, Map<String, Serializable> options) {
//        game.end();
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Cards cards, TargetCard target, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseTarget(Outcome outcome, Target target, Ability source, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseTarget(Outcome outcome, Cards cards, TargetCard target, Ability source, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseTargetAmount(Outcome outcome, TargetAmount target, Ability source, Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseMulligan(Game game) {
//        game.end();
//    }
//
//    @Override
//    public boolean chooseUse(Outcome outcome, String message, Game game) {
//        game.pause();
//        nextAction = NextAction.CHOOSE_USE;
//        return false;
//    }
//
//    @Override
//    public boolean choose(Outcome outcome, Choice choice, Game game) {
//        game.end();
//    }
//
//    @Override
//    public int chooseEffect(List<ReplacementEffect> rEffects, Game game) {
//        game.end();
//    }
//
//    @Override
//    public TriggeredAbility chooseTriggeredAbility(TriggeredAbilities abilities, Game game) {
//        game.end();
//    }
//
//    @Override
//    public Mode chooseMode(Modes modes, Ability source, Game game) {
//        game.end();
//    }

    @Override
    public void selectAttackers(Game game, UUID attackingPlayerId) {
        game.pause();
        nextAction = NextAction.SELECT_ATTACKERS;
    }

    @Override
    public void selectBlockers(Game game, UUID defendingPlayerId) {
        game.pause();
        nextAction = NextAction.SELECT_BLOCKERS;
    }

//    @Override
//    public UUID chooseAttackerOrder(List<Permanent> attacker, Game game) {
//        game.end();
//    }
//
//    @Override
//    public UUID chooseBlockerOrder(List<Permanent> blockers, Game game) {
//        game.end();
//    }
//
//    @Override
//    public void assignDamage(int damage, List<UUID> targets, String singleTargetName, UUID sourceId, Game game) {
//        game.end();
//    }
//
//    @Override
//    public int getAmount(int min, int max, String message, Game game) {
//        game.end();
//    }

}
