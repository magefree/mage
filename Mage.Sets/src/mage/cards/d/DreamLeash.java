
package mage.cards.d;

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
import mage.target.TargetPermanent;
import mage.target.common.TargetTappedPermanentAsYouCast;

import java.util.UUID;

/**
 *
 * @author maxlebedev
 */
public final class DreamLeash extends CardImpl {

    public DreamLeash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        TargetPermanent auraTarget = new TargetTappedPermanentAsYouCast();
        auraTarget.withChooseHint("must be tapped");
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // You can't choose an untapped permanent as Dream Leash's target as you cast Dream Leash.
        Effect effect = new ControlEnchantedEffect("permanent");
        effect.setText("You can't choose an untapped permanent as {this}'s target as you cast {this}.<br>" + effect.getText(null));

        // You control enchanted permanent.
        this.addAbility(new SimpleStaticAbility(effect));
    }

    private DreamLeash(final DreamLeash card) {
        super(card);
    }

    @Override
    public DreamLeash copy() {
        return new DreamLeash(this);
    }
}