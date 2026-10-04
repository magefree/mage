
package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.HasteAbility;
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
 * @author North
 */
public final class GoblinWarPaint extends CardImpl {

    public GoblinWarPaint(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+2 and has haste.
        this.addAbility(new SimpleStaticAbility(new BoostGainAbilityGenericEffect(
                2, 2, Duration.WhileOnBattlefield, HasteAbility.getInstance()
        ).setTargetPointer(new SourceAttachedTargetPointer("enchanted creature"))));
    }

    private GoblinWarPaint(final GoblinWarPaint card) {
        super(card);
    }

    @Override
    public GoblinWarPaint copy() {
        return new GoblinWarPaint(this);
    }
}
