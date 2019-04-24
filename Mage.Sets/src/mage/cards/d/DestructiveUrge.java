
package mage.cards.d;

import java.util.UUID;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author TheElk801
 */
public final class DestructiveUrge extends CardImpl {

    public DestructiveUrge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{R}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature deals combat damage to a player, that player sacrifices a land.
        ability = new DealsDamageToAPlayerAttachedTriggeredAbility(new SacrificeEffect(new FilterLandPermanent(), 1, "that player"), "enchanted", false, true);
        this.addAbility(ability);
    }

    public DestructiveUrge(final DestructiveUrge card) {
        super(card);
    }

    @Override
    public DestructiveUrge copy() {
        return new DestructiveUrge(this);
    }
}
