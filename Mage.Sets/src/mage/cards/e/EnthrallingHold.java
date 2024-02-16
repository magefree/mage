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
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetTappedPermanentAsYouCast;

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
        TargetPermanent auraTarget = new TargetTappedPermanentAsYouCast(StaticFilters.FILTER_PERMANENT_CREATURE);
        auraTarget.withChooseHint("must be tapped");
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You can't choose an untapped creature as this spell's target as you cast it.
        Effect effect = new ControlEnchantedEffect();
        effect.setText("You can't choose an untapped creature as this spell's target as you cast it.<br>" + effect.getText(null));

        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private EnthrallingHold(final EnthrallingHold card) {
        super(card);
    }

    @Override
    public EnthrallingHold copy() {
        return new EnthrallingHold(this);
    }
}
