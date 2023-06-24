
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Markedagain
 */
public final class FencersMagemark extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Creatures you control that are enchanted");
    static {
        filter.add(EnchantedPredicate.instance);
        filter.add(TargetController.YOU.getControllerPredicate());
    }
    
    public FencersMagemark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Creatures you control that are enchanted get +1/+1 and have first strike.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1,1, Duration.WhileOnBattlefield, filter, false));
        Effect effect = new GainAbilityAllEffect(FirstStrikeAbility.getInstance(),Duration.WhileOnBattlefield,filter, " and have first strike");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    private FencersMagemark(final FencersMagemark card) {
        super(card);
    }

    @Override
    public FencersMagemark copy() {
        return new FencersMagemark(this);
    }
}
