package mage.abilities.effects.common;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.Target;
import mage.target.targetpointer.FirstTargetPointer;
import mage.target.targetpointer.SecondTargetPointer;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ExileTargetEffect extends OneShotEffect {

    private final Zone onlyFromZone;
    private String exileZone = null;
    private UUID exileId = null;
    private boolean toSourceExileZone = false; // exile the targets to a source object specific exile zone (takes care of zone change counter)
    private boolean withName = true;

    public ExileTargetEffect(String effectText) {
        this();
        this.staticText = effectText;
    }

    /**
     * Exile cards to normal exile window (but it can exile to source's exile
     * window after toSourceExileZone change)
     */
    public ExileTargetEffect() {
        this(null, "");
    }

    public ExileTargetEffect(UUID exileId, String exileZone) {
        this(exileId, exileZone, null);
    }

    public ExileTargetEffect(UUID exileId, String exileZone, Zone onlyFromZone) {
        super(Outcome.Exile);
        this.exileZone = exileZone;
        this.exileId = exileId;
        this.onlyFromZone = onlyFromZone;
    }

    public ExileTargetEffect(final ExileTargetEffect effect) {
        super(effect);
        this.exileZone = effect.exileZone;
        this.exileId = effect.exileId;
        this.onlyFromZone = effect.onlyFromZone;
        this.toSourceExileZone = effect.toSourceExileZone;
        this.withName = effect.withName;
    }

    @Override
    public ExileTargetEffect copy() {
        return new ExileTargetEffect(this);
    }

    public ExileTargetEffect setToSourceExileZone(boolean toSourceExileZone) {
        this.toSourceExileZone = toSourceExileZone;
        return this;
    }

    public void setWithName(boolean withName) {
        this.withName = withName;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Set<Card> toExile = new LinkedHashSet<>();
            for (UUID targetId : getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null
                        && permanent.isPhasedIn()) {
                    Zone currentZone = game.getState().getZone(permanent.getId());
                    if (currentZone != Zone.EXILED
                            && (onlyFromZone == null
                            || onlyFromZone == Zone.BATTLEFIELD)) {
                        toExile.add(permanent);
                    }
                } else {
                    Card card = game.getCard(targetId);
                    if (card != null) {
                        Zone currentZone = game.getState().getZone(card.getId());
                        if (currentZone != Zone.EXILED
                                && (onlyFromZone == null
                                || onlyFromZone == currentZone)) {
                            toExile.add(card);
                        }
                    } else {
                        StackObject stackObject = game.getStack().getStackObject(targetId);
                        if (stackObject instanceof Spell) {
                            toExile.add((Spell) stackObject);
                        }
                    }
                }
            }
            if (toSourceExileZone) {
                MageObject sourceObject = source.getSourceObject(game);
                exileId = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
                if (sourceObject != null) {
                    exileZone = sourceObject.getIdName();
                }
            }
            controller.moveCardsToExile(toExile, source, game, withName, exileId, exileZone);
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (mode.getTargets().isEmpty()) {
            sb.append("exile that permanent"); // this will be used if the target is set by target pointer and staticText not set.
        } else {
            Target target;
            if (targetPointer instanceof SecondTargetPointer && mode.getTargets().size() > 1) {
                target = mode.getTargets().get(1);
            } else {
                target = mode.getTargets().get(0);
            }
            if (target.getNumberOfTargets() == 1) {
                String targetName = target.getTargetName();
                sb.append("exile ");
                if (!targetName.startsWith("another")) {
                    sb.append("target ");
                }
                sb.append(targetName);
            } else if (target.getNumberOfTargets() == 0 && target.getMaxNumberOfTargets() > 0) {
                sb.append("exile up to ").append(CardUtil.numberToText(target.getMaxNumberOfTargets())).append(" target ").append(target.getTargetName());
            } else {
                sb.append("exile ").append(CardUtil.numberToText(target.getNumberOfTargets())).append(" target ").append(target.getTargetName());
            }
        }
        return sb.toString();
    }
}
