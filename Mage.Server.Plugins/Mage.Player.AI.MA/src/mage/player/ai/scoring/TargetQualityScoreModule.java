package mage.player.ai.scoring;

import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.constants.CommanderCardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.stack.StackObject;
import mage.player.ai.AiStrategyScore;
import mage.players.Player;
import mage.target.Target;
import mage.util.CardUtil;
import mage.watchers.common.CommanderPlaysCountWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public final class TargetQualityScoreModule implements AiScoreModule {

    private static final String ENABLED_PROPERTY = "xmage.ai.strategy.targetQuality.enabled";
    private static final String APPLY_PROPERTY = "xmage.ai.strategy.targetQuality.apply";
    private static final String MAX_MODIFIER_PROPERTY = "xmage.ai.strategy.targetQuality.maxScoreModifier";

    private final boolean globalApplyModifiers;

    public TargetQualityScoreModule(AiScoreModuleConfig config) {
        this.globalApplyModifiers = config != null && config.isApplyModifiers();
    }

    @Override
    public String getName() {
        return "target-quality";
    }

    @Override
    public AiStrategyScore evaluate(AiScoringContext context) {
        if (context == null) {
            return AiStrategyScore.none(0);
        }
        if (!AiScoreSupport.isEnabled(ENABLED_PROPERTY)) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        Game game = context.getDecisionGame();
        Ability action = context.getAction();
        UUID playerId = context.getPlayerId();
        if (game == null || action == null || playerId == null || action.getAllSelectedTargets().isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        ActionPolarity polarity = ActionPolarity.from(action);
        if (polarity == ActionPolarity.NEUTRAL) {
            return AiStrategyScore.none(context.getBaseScore());
        }

        List<AiStrategyScore.Contribution> contributions = new ArrayList<>();
        for (Target target : action.getAllSelectedTargets()) {
            for (UUID targetId : target.getTargets()) {
                TargetAssessment assessment = assessTarget(game, action, playerId, targetId, polarity);
                if (assessment.value != 0) {
                    contributions.add(new AiStrategyScore.Contribution(
                            "target-quality:" + assessment.label,
                            assessment.value,
                            assessment.detail
                    ));
                }
            }
        }

        if (contributions.isEmpty()) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        int raw = contributions.stream().mapToInt(AiStrategyScore.Contribution::getValue).sum();
        raw = AiScoreSupport.clamp(raw, AiScoreSupport.moduleCap(MAX_MODIFIER_PROPERTY, 45));
        if (raw == 0) {
            return AiStrategyScore.none(context.getBaseScore());
        }
        return AiStrategyScore.of(
                context.getBaseScore(),
                raw,
                AiScoreSupport.apply(raw, globalApplyModifiers, APPLY_PROPERTY),
                contributions
        );
    }

    private static TargetAssessment assessTarget(Game game,
                                                 Ability action,
                                                 UUID playerId,
                                                 UUID targetId,
                                                 ActionPolarity polarity) {
        if (targetId == null) {
            return TargetAssessment.none();
        }

        Player targetPlayer = game.getPlayer(targetId);
        if (targetPlayer != null) {
            boolean friendly = targetPlayer.getId().equals(playerId);
            if (polarity == ActionPolarity.HOSTILE) {
                if (friendly) {
                    return new TargetAssessment("bad-friendly-player", -35, "hostile action targets self");
                }
                int value = targetPlayer.getLife() <= 8 ? 28 : 12;
                return new TargetAssessment("enemy-player", value, "hostile action targets " + targetPlayer.getName());
            }
            return friendly
                    ? new TargetAssessment("friendly-player", 12, "beneficial action targets self")
                    : new TargetAssessment("bad-enemy-player", -28, "beneficial action targets opponent " + targetPlayer.getName());
        }

        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null) {
            return assessPermanent(game, action, playerId, permanent, polarity);
        }

        StackObject stackObject = game.getStack().getStackObject(targetId);
        if (stackObject != null) {
            boolean friendly = playerId.equals(stackObject.getControllerId());
            if (polarity == ActionPolarity.HOSTILE) {
                return friendly
                        ? new TargetAssessment("bad-friendly-stack", -35, "hostile stack action targets own " + stackObject.getName())
                        : new TargetAssessment("enemy-stack", stackObjectPriority(stackObject), "hostile stack action targets " + stackObject.getName());
            }
            return friendly
                    ? new TargetAssessment("friendly-stack", 20, "protects own stack object " + stackObject.getName())
                    : new TargetAssessment("bad-enemy-stack", -30, "beneficial action targets opponent stack object " + stackObject.getName());
        }

        return TargetAssessment.none();
    }

    private static TargetAssessment assessPermanent(Game game,
                                                    Ability action,
                                                    UUID playerId,
                                                    Permanent permanent,
                                                    ActionPolarity polarity) {
        boolean friendly = permanent.isControlledBy(playerId);
        PermanentQuality quality = permanentQuality(game, playerId, permanent);
        if (polarity == ActionPolarity.HOSTILE) {
            if (friendly) {
                return new TargetAssessment("bad-friendly-permanent", -Math.max(25, quality.value),
                        "hostile action targets own " + permanent.getName());
            }
            if (quality.lowImpact) {
                return new TargetAssessment("low-impact-enemy", -12,
                        "hostile action targets low-impact " + permanent.getName());
            }
            return new TargetAssessment("enemy-" + quality.label, quality.value,
                    "hostile action targets " + quality.detail);
        }
        if (!friendly) {
            return new TargetAssessment("bad-enemy-permanent", -Math.max(20, quality.value),
                    "beneficial action targets opponent " + permanent.getName());
        }
        if (quality.lowImpact && !isCounterLikeProtection(action, game)) {
            return new TargetAssessment("low-impact-friendly", -10,
                    "beneficial action targets low-impact " + permanent.getName());
        }
        return new TargetAssessment("friendly-" + quality.label, quality.value,
                "beneficial action targets " + quality.detail);
    }

    private static PermanentQuality permanentQuality(Game game, UUID playerId, Permanent permanent) {
        String rules = AiScoreSupport.rules(permanent);
        boolean commander = isCommanderPermanent(game, playerId, permanent);
        int commanderTax = commander ? commanderTaxMana(game, playerId, permanent) : 0;
        if (commander) {
            int value = Math.min(45, 32 + commanderTax * 4);
            return new PermanentQuality("commander", value, "commander " + permanent.getName() + " tax +" + commanderTax, false);
        }
        if (isEngineText(rules)) {
            return new PermanentQuality("engine", 32, "engine " + permanent.getName(), false);
        }
        if (isPayoffText(rules)) {
            return new PermanentQuality("payoff", 27, "payoff " + permanent.getName(), false);
        }
        if (permanent.isCreature(game)) {
            int power = Math.max(0, permanent.getPower().getValue());
            int toughness = Math.max(0, permanent.getToughness().getValue());
            int body = power + toughness;
            if (body >= 10 || hasCombatKeyword(rules)) {
                return new PermanentQuality("threat", Math.min(28, 12 + body), "threat " + permanent.getName()
                        + " [" + power + "/" + toughness + "]", false);
            }
            boolean token = permanent instanceof PermanentToken;
            int value = Math.min(14, 4 + power + toughness);
            return new PermanentQuality("body", value, permanent.getName() + " [" + power + "/" + toughness + "]", token || body <= 2);
        }
        if (rules.contains("add {") || rules.contains("add one mana")) {
            return new PermanentQuality("resource", 16, "resource " + permanent.getName(), false);
        }
        return new PermanentQuality("utility", 10, "utility " + permanent.getName(), false);
    }

    private static boolean isEngineText(String rules) {
        return rules.contains("whenever")
                || rules.contains("at the beginning")
                || rules.contains("you win the game")
                || rules.contains("draw a card")
                || rules.contains("create a token")
                || rules.contains("create ") && rules.contains(" token");
    }

    private static boolean isPayoffText(String rules) {
        return rules.contains("creatures you control")
                || rules.contains("other creatures")
                || rules.contains("tokens you control")
                || rules.contains("whenever you cast")
                || rules.contains("for each")
                || rules.contains("+1/+1 counter");
    }

    private static boolean hasCombatKeyword(String rules) {
        return rules.contains("flying")
                || rules.contains("double strike")
                || rules.contains("first strike")
                || rules.contains("deathtouch")
                || rules.contains("lifelink")
                || rules.contains("trample")
                || rules.contains("vigilance");
    }

    private static boolean isCommanderPermanent(Game game, UUID playerId, Permanent permanent) {
        try {
            Player player = game.getPlayer(playerId);
            return player != null
                    && game.getCommandersIds(player, CommanderCardType.COMMANDER_OR_OATHBREAKER, true)
                    .contains(permanent.getId());
        } catch (RuntimeException e) {
            return false;
        }
    }

    private static int commanderTaxMana(Game game, UUID playerId, Permanent permanent) {
        try {
            Player player = game.getPlayer(playerId);
            CommanderPlaysCountWatcher watcher = game.getState().getWatcher(CommanderPlaysCountWatcher.class);
            if (player == null || watcher == null || !game.isCommanderObject(player, permanent)) {
                return 0;
            }
            UUID mainCardId = CardUtil.getMainCardId(game, permanent.getId());
            return 2 * watcher.getPlaysCount(mainCardId);
        } catch (RuntimeException e) {
            return 0;
        }
    }

    private static int stackObjectPriority(StackObject stackObject) {
        String text = (stackObject.getName() + " " + AiScoreSupport.rules(stackObject)).toLowerCase(Locale.ROOT);
        if (text.contains("you win the game") || text.contains("extra turn") || text.contains("destroy all")) {
            return 45;
        }
        if (text.contains("draw") || text.contains("create") || text.contains("return")) {
            return 32;
        }
        return 24;
    }

    private static boolean isCounterLikeProtection(Ability action, Game game) {
        String text = AiScoreSupport.actionText(action, game);
        return text.contains("counter target")
                || text.contains("hexproof")
                || text.contains("indestructible")
                || text.contains("protection from");
    }

    private enum ActionPolarity {
        HOSTILE,
        BENEFICIAL,
        NEUTRAL;

        private static ActionPolarity from(Ability action) {
            boolean good = false;
            boolean bad = false;
            for (Effect effect : action.getAllEffects()) {
                Outcome outcome = effect.getOutcome();
                if (outcome == null) {
                    continue;
                }
                if (outcome.isGood()) {
                    good = true;
                } else {
                    bad = true;
                }
            }
            if (bad && !good) {
                return HOSTILE;
            }
            if (good && !bad) {
                return BENEFICIAL;
            }
            return NEUTRAL;
        }
    }

    private static final class PermanentQuality {
        private final String label;
        private final int value;
        private final String detail;
        private final boolean lowImpact;

        private PermanentQuality(String label, int value, String detail, boolean lowImpact) {
            this.label = label;
            this.value = value;
            this.detail = detail;
            this.lowImpact = lowImpact;
        }
    }

    private static final class TargetAssessment {
        private final String label;
        private final int value;
        private final String detail;

        private TargetAssessment(String label, int value, String detail) {
            this.label = label;
            this.value = value;
            this.detail = detail;
        }

        private static TargetAssessment none() {
            return new TargetAssessment("", 0, "");
        }
    }
}
