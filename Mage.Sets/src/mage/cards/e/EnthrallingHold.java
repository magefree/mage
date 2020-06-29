package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author htrajan
 */
public final class EnthrallingHold extends CardImpl {

    public EnthrallingHold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new EnthrallingHoldTarget();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // You can't choose an untapped creature as this spell's target as you cast it.
        Effect controlEnchantedEffect = new ControlEnchantedEffect();
        controlEnchantedEffect.setText("You can't choose an untapped creature as this spell's target as you cast it.<br>" + controlEnchantedEffect.getText(null));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, controlEnchantedEffect));
    }

    private EnthrallingHold(final EnthrallingHold card) {
        super(card);
    }

    @Override
    public EnthrallingHold copy() {
        return new EnthrallingHold(this);
    }
}

class EnthrallingHoldTarget extends TargetCreaturePermanent {

    EnthrallingHoldTarget() {}

    private EnthrallingHoldTarget(EnthrallingHoldTarget target) {
        super(target);
    }

    @Override
    public EnthrallingHoldTarget copy() {
        return new EnthrallingHoldTarget(this);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        if (super.canTarget(controllerId, id, source, game)) {
            Permanent permanent = game.getPermanent(id);
            return permanent.isTapped();
        }
        return false;
    }

    // See ruling: https://www.mtgsalvation.com/forums/magic-fundamentals/magic-rulings/magic-rulings-archives/253345-dream-leash
    @Override
    public boolean stillLegalTarget(UUID id, Ability source, Game game) {
        Permanent permanent = game.getPermanent(id);
        return permanent != null && StaticFilters.FILTER_PERMANENT_CREATURE.match(permanent, game);
    }
}