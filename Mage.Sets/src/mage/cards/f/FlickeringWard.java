
package mage.cards.f;

import java.util.UUID;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.effects.keyword.ProtectionChosenColorAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class FlickeringWard extends CardImpl {

    public FlickeringWard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Protect));
        this.addAbility(new EnchantAbility(auraTarget));

        // As Flickering Ward enters the battlefield, choose a color.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Benefit)));

        // Enchanted creature has protection from the chosen color. This effect doesn't remove Flickering Ward.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ProtectionChosenColorAttachedEffect(true)));

        // {W}: Return Flickering Ward to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(), new ManaCostsImpl<>("{W}")));
    }

    private FlickeringWard(final FlickeringWard card) {
        super(card);
    }

    @Override
    public FlickeringWard copy() {
        return new FlickeringWard(this);
    }
}
