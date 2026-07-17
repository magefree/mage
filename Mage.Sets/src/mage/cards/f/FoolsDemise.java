package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlTargetEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
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
 *
 * @author LevelX2
 */
public final class FoolsDemise extends CardImpl {

    public FoolsDemise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}");
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

        // When Fool's Demise is put into a graveyard from the battlefield, return Fool's Demise to its owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ReturnToHandSourceEffect()));
    }

    private FoolsDemise(final FoolsDemise card) {
        super(card);
    }

    @Override
    public FoolsDemise copy() {
        return new FoolsDemise(this);
    }
}
