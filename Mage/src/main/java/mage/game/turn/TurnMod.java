package mage.game.turn;

import mage.abilities.Ability;
import mage.constants.PhaseStep;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.util.CardUtil;
import mage.util.Copyable;

import java.io.Serializable;
import java.util.UUID;

/**
 * Creates a signle turn modification for turn, phase or step
 * <p>
 * For one time usage only
 * For current turn only
 * <p>
 * If you need it in continuous effect then use ContinuousRuleModifyingEffectImpl
 * with game events like UNTAP_STEP (example: Sands of Time)
 * <p>
 * Supports:
 * - new turn controller
 * - turn: extra and skip
 * - phase: extra and skip
 * - step: extra and skip
 *
 * @author JayDi85
 */
public class TurnMod implements Serializable, Copyable<TurnMod> {

    private final UUID id;
    private final UUID playerId;

    private UUID newControllerId;

    private boolean extraTurn;
    private boolean skipTurn;

    private TurnPhase extraPhase;
    private TurnPhase skipPhase;

    private Step extraStep;
    private PhaseStep skipStep;

    private TurnPhase afterPhase;
    private PhaseStep afterStep;

    private boolean locked = false; // locked for modification, used for wrong code usage protection
    private String tag; // for inner usage like enable/disable mod in effects
    private String info; // for GUI usage like additional info in logs

    // Turn mod that should be applied after current turn mod
    // Implemented only for new controller turn mod
    private TurnMod subsequentTurnMod;

    private TurnMod(final TurnMod mod) {
        this.id = mod.id;
        this.playerId = mod.playerId;
        this.newControllerId = mod.newControllerId;
        this.extraTurn = mod.extraTurn;
        this.skipTurn = mod.skipTurn;
        this.extraPhase = mod.extraPhase;
        this.skipPhase = mod.skipPhase;
        if (mod.extraStep != null) {
            this.extraStep = mod.extraStep.copy();
        }
        this.skipStep = mod.skipStep;
        this.afterPhase = mod.afterPhase;
        this.afterStep = mod.afterStep;
        if (mod.subsequentTurnMod != null) {
            this.subsequentTurnMod = mod.subsequentTurnMod.copy();
        }
        this.tag = mod.tag;
        this.info = mod.info;
        this.locked = mod.locked;
    }

    public TurnMod copy() {
        return new TurnMod(this);
    }

    public TurnMod(UUID playerId) {
        // TODO: delete
        this.id = UUID.randomUUID();
        this.playerId = playerId;
    }

    private void lock() {
        if (this.locked) {
            throw new IllegalStateException("Wrong code usage: you must use only one type of turn modification");
        }
        this.locked = true;
    }

    public TurnMod withSkipTurn() {
        this.skipTurn = true;
        lock();
        return this;
    }

    public TurnMod withExtraTurn() {
        this.extraTurn = true;
        lock();
        return this;
    }

    public TurnMod withNewController(UUID newControllerId) {
        return withNewController(newControllerId, null);
    }

    public TurnMod withNewController(UUID newControllerId, TurnMod nextSubsequentTurnMod) {
        this.newControllerId = newControllerId;
        this.subsequentTurnMod = nextSubsequentTurnMod;
        lock();
        return this;
    }

    public TurnMod withSkipPhase(TurnPhase skipPhase) {
        this.skipPhase = skipPhase;
        lock();
        return this;
    }

    public TurnMod withExtraPhase(TurnPhase extraPhase) {
        return withExtraPhase(extraPhase, null);
    }

    public TurnMod withExtraPhase(TurnPhase extraPhase, TurnPhase addAfterPhase) {
        this.extraPhase = extraPhase;
        this.afterPhase = addAfterPhase;
        lock();
        return this;
    }

    public TurnMod withSkipStep(PhaseStep skipStep) {
        this.skipStep = skipStep;
        lock();
        return this;
    }

    public TurnMod withExtraStep(Step extraStep) {
        return withExtraStep(extraStep, null);
    }

    public TurnMod withExtraStep(Step extraStep, PhaseStep addAfterStep) {
        this.extraStep = extraStep;
        this.afterStep = addAfterStep;
        lock();
        return this;
    }

    public TurnMod withTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public TurnMod withInfo(String info) {
        this.info = info;
        return this;
    }

    public String getInfo() {
        return info == null ? "" : info;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public boolean isExtraTurn() {
        return extraTurn;
    }

    public boolean isSkipTurn() {
        return skipTurn;
    }

    public TurnPhase getExtraPhase() {
        return extraPhase;
    }

    public Step getExtraStep() {
        return extraStep;
    }

    public TurnPhase getSkipPhase() {
        return skipPhase;
    }

    public PhaseStep getSkipStep() {
        return skipStep;
    }

    public TurnPhase getAfterPhase() {
        return afterPhase;
    }

    public PhaseStep getAfterStep() {
        return afterStep;
    }

    public UUID getNewControllerId() {
        return newControllerId;
    }

    public UUID getId() {
        return id;
    }

    public TurnMod getSubsequentTurnMod() {
        return subsequentTurnMod;
    }

    public boolean isLocked() {
        return locked;
    }

    private void addSourceAsInfo(Game game, Ability source) {
        this.info = CardUtil.getSourceLogName(game, source);
    }
}