
package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.continuous.BoostGainAbilityGenericEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.TrampleAbility;
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
 * @author noxx
 */
public final class Rancor extends CardImpl {

    public Rancor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +2/+0 and has trample.
        this.addAbility(new SimpleStaticAbility(new BoostGainAbilityGenericEffect(
                2, 0, Duration.WhileOnBattlefield, TrampleAbility.getInstance()
        ).setTargetPointer(new SourceAttachedTargetPointer("enchanted creature"))));

        // When Rancor is put into a graveyard from the battlefield, return Rancor to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private Rancor(final Rancor card) {
        super(card);
    }

    @Override
    public Rancor copy() {
        return new Rancor(this);
    }
}
