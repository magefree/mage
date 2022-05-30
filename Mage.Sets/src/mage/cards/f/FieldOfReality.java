
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX
 */
public final class FieldOfReality extends CardImpl {

    public FieldOfReality(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // Enchanted creature can't be blocked by Spirits.
        this.addAbility(new SimpleEvasionAbility(new CantBeBlockedByCreaturesAttachedEffect(
                Duration.WhileOnBattlefield, new FilterCreaturePermanent(SubType.SPIRIT, "Spirits"), AttachmentType.AURA)));
        // {1}{U}: Return Field of Reality to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{1}{U}")));
    }

    private FieldOfReality(final FieldOfReality card) {
        super(card);
    }

    @Override
    public FieldOfReality copy() {
        return new FieldOfReality(this);
    }

}
