
package mage.cards.d;

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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author maxlebedev
 */
public final class DreamLeash extends CardImpl {

    public DreamLeash(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");
        this.subtype.add(SubType.AURA);

        // Enchant permanent
        // You can't choose an untapped permanent as Dream Leash's target as you cast Dream Leash.
        TargetPermanent auraTarget = new DreamLeashTarget();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // You control enchanted permanent.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect("permanent")));
    }

    private DreamLeash(final DreamLeash card) {
        super(card);
    }

    @Override
    public DreamLeash copy() {
        return new DreamLeash(this);
    }
}


class DreamLeashTarget extends TargetPermanent {

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {

        if(super.canTarget(controllerId, id, source, game)){
            Permanent permanent = game.getPermanent(id);
            return permanent.isTapped();
        }
        return false;
    }

}
