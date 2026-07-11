package mage.cards;

import mage.abilities.costs.mana.ManaCost;
import mage.abilities.SpellAbility;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * The spell ability of a {@link PrepareSpellCard}, representing "While it's prepared, you may cast a
 * copy of its spell" (CR 722.4).
 * <p>
 * It is offered from the battlefield (its zone is {@link Zone#BATTLEFIELD}) and can only be activated
 * while the parent permanent is prepared. It carries the prepare spell's timing so a sorcery-typed
 * prepare spell can only be cast at sorcery speed. Casting it creates and casts a copy of the prepare
 * spell (see {@link PrepareSpellCard#cast}).
 *
 * @author wakame1367
 */
public class PrepareSpellAbility extends SpellAbility {

    private final PrepareCard prepareCardParent;

    public PrepareSpellAbility(ManaCost cost, String preparationName, boolean instantSpeed, PrepareCard prepareCardParent) {
        super(cost, preparationName, Zone.BATTLEFIELD, SpellAbilityType.PREPARE_SPELL);
        this.setTiming(instantSpeed ? TimingRule.INSTANT : TimingRule.SORCERY);
        this.prepareCardParent = prepareCardParent;
    }

    protected PrepareSpellAbility(final PrepareSpellAbility ability) {
        super(ability);
        this.prepareCardParent = ability.prepareCardParent;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        // only a prepared permanent may cast a copy of its prepare spell
        Permanent permanent = game.getPermanent(prepareCardParent.getId());
        if (permanent == null || !permanent.isPrepared()) {
            return ActivationStatus.getFalse();
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public PrepareSpellAbility copy() {
        return new PrepareSpellAbility(this);
    }
}
