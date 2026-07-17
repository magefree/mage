package mage.cards.u;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author noxx
 */
public final class UnhallowedPact extends CardImpl {

    public UnhallowedPact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // When enchanted creature dies, return that card to the battlefield under your control.
        this.addAbility(new DiesAttachedTriggeredAbility(new ReturnToBattlefieldUnderYourControlTargetEffect(),
                "enchanted creature", false, true, SetTargetPointer.CARD));
    }

    private UnhallowedPact(final UnhallowedPact card) {
        super(card);
    }

    @Override
    public UnhallowedPact copy() {
        return new UnhallowedPact(this);
    }
}
