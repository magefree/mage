package mage.abilities.effects.common.combat;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.PayCostToAttackBlockEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 * @author LevelX2
 */
public class CantAttackBlockUnlessPaysSourceEffect extends PayCostToAttackBlockEffectImpl {

    public CantAttackBlockUnlessPaysSourceEffect(Cost cost, RestrictType restrictType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, restrictType, cost);
        staticText = "{this} can't " + restrictType.toString() + " unless you " + (cost == null ? "" : cost.getText())
                + (restrictType == RestrictType.ATTACK ? ". <i>(This cost is paid as attackers are declared.)</i>" : "");
    }

    public CantAttackBlockUnlessPaysSourceEffect(ManaCosts manaCosts, RestrictType restrictType) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, RestrictType.ATTACK_AND_BLOCK, manaCosts);
        staticText = "{this} can't " + restrictType.toString() + " unless you pay " + (manaCosts == null ? "" : manaCosts.getText());
    }

    protected CantAttackBlockUnlessPaysSourceEffect(final CantAttackBlockUnlessPaysSourceEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(restrictType == RestrictType.BLOCK) && event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            return event.getSourceId().equals(source.getSourceId());
        }
        if (!(restrictType == RestrictType.ATTACK) && event.getType() == GameEvent.EventType.DECLARE_BLOCKER) {
            return event.getSourceId().equals(source.getSourceId());
        }
        return false;
    }

    @Override
    public CantAttackBlockUnlessPaysSourceEffect copy() {
        return new CantAttackBlockUnlessPaysSourceEffect(this);
    }
}
