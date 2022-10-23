
package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
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
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature deals combat damage to a player, that player sacrifices a land.
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new SacrificeEffect(StaticFilters.FILTER_LAND, 1, "that player"),
                "enchanted", false, true
        ));
    }

    private DestructiveUrge(final DestructiveUrge card) {
        super(card);
    }

    @Override
    public DestructiveUrge copy() {
        return new DestructiveUrge(this);
    }
}
