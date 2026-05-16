/**
 * Modular strategic scoring for the experimental XMage AI.
 * <p>
 * The original Mad AI remains the baseline evaluator. The strategic player wraps that baseline by
 * running ordered {@link mage.player.ai.scoring.AiScoreModule} instances over each top-level candidate
 * and adding traceable score contributions. Modules should stay generic, phase-aware where useful,
 * and configurable through {@code xmage.ai.strategy.*} system properties so they can run in monitor
 * mode before influencing live choices.
 */
package mage.player.ai.scoring;
