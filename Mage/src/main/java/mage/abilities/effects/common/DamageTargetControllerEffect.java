package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author LoneFox
 */
public class DamageTargetControllerEffect extends OneShotEffect {

    protected DynamicValue amount;
    protected boolean preventable;
    protected boolean useLastKnownInfo;

    public DamageTargetControllerEffect(int amount) {
        this(StaticValue.get(amount), true);
    }

    public DamageTargetControllerEffect(int amount, boolean preventable) {
        this(StaticValue.get(amount), preventable);
    }
    
    public DamageTargetControllerEffect(int amount, boolean preventable, boolean useLastKnownInfo) {
        this(StaticValue.get(amount), preventable);
        this.useLastKnownInfo = useLastKnownInfo;
    }

    public DamageTargetControllerEffect(DynamicValue amount) {
        this(amount, true);
    }

    public DamageTargetControllerEffect(DynamicValue amount, boolean preventable) {
        super(Outcome.Damage);
        this.amount = amount;
        this.preventable = preventable;
    }

    public DamageTargetControllerEffect(final DamageTargetControllerEffect effect) {
        super(effect);
        amount = effect.amount.copy();
        preventable = effect.preventable;
        useLastKnownInfo = effect.useLastKnownInfo;
    }

    @Override
    public DamageTargetControllerEffect copy() {
        return new DamageTargetControllerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));

        if (permanent == null && useLastKnownInfo){
            Card card = game.getCard(targetPointer.getFirst(game, source));

            if (card != null) {
                permanent = (Permanent) game.getLastKnownInformation(card.getId(), Zone.BATTLEFIELD);
            }
        }
        
        if (permanent != null) {
            Player targetController = game.getPlayer(permanent.getControllerId());
            if (targetController != null) {
                targetController.damage(amount.calculate(game, source, this), source.getSourceId(), game, false, preventable);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        String text = "{this} deals " + amount.getMessage() + " damage to target "
                + mode.getTargets().get(0).getTargetName() + "'s controller";
        if (!preventable) {
            text += ". The damage can't be prevented";
        }
        return text;
    }
}
