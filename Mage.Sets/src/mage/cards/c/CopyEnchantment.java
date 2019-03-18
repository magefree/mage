
package mage.cards.c;

import java.util.UUID;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentCard;
import mage.players.Player;
import mage.target.Target;
import mage.util.functions.EmptyApplyToPermanent;

/**
 *
 * @author LevelX2
 */
public final class CopyEnchantment extends CardImpl {

    public CopyEnchantment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{U}");

        // You may have Copy Enchantment enter the battlefield as a copy of any enchantment on the battlefield.
        //this.addAbility(new EntersBattlefieldAbility(new CopyEnchantmentEffect(new FilterEnchantmentPermanent("any enchantment")), true));
        this.addAbility(new EntersBattlefieldAbility(new CopyPermanentEffect(StaticFilters.FILTER_ENCHANTMENT_PERMANENT), true));
    }

    public CopyEnchantment(final CopyEnchantment card) {
        super(card);
    }

    @Override
    public CopyEnchantment copy() {
        return new CopyEnchantment(this);
    }
}
