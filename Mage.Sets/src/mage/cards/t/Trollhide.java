

package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SourceAttachedTargetPointer;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class Trollhide extends CardImpl {

    public Trollhide (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and has "{1}{G}: Regenerate this creature."
        this.addAbility(new SimpleStaticAbility(new BoostGainAbilityGenericEffect(
                2, 2, Duration.WhileOnBattlefield,
                new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl<>("{1}{G}"))
        ).withTargetObjectName("creature")
                .setTargetPointer(new SourceAttachedTargetPointer("enchanted creature"))));
    }

    private Trollhide(final Trollhide card) {
        super(card);
    }

    @Override
    public Trollhide copy() {
        return new Trollhide(this);
    }

}
