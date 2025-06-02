
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddPlusOneCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ForcedAdaptation extends CardImpl {

    public ForcedAdaptation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");
        this.subtype.add(SubType.AURA);


        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, put a +1/+1 counter on enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddPlusOneCountersAttachedEffect(1)));
    }

    private ForcedAdaptation(final ForcedAdaptation card) {
        super(card);
    }

    @Override
    public ForcedAdaptation copy() {
        return new ForcedAdaptation(this);
    }
}
