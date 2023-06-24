
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Quercitron
 */
public final class Conquer extends CardImpl {

    public Conquer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}{R}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // You control enchanted land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect("land")));
    }

    private Conquer(final Conquer card) {
        super(card);
    }

    @Override
    public Conquer copy() {
        return new Conquer(this);
    }
}
