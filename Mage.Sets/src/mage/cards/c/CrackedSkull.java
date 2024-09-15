package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.DealtDamageAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.discard.LookTargetHandChooseDiscardEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrackedSkull extends CardImpl {

    public CrackedSkull(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Cracked Skull enters, look at target player's hand. You may choose a nonland card from it. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new LookTargetHandChooseDiscardEffect(true, StaticValue.get(1), StaticFilters.FILTER_CARD_NON_LAND)
                        .setText("look at target player's hand. You may choose a nonland card from it. That player discards that card")
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When enchanted creature is dealt damage, destroy it.
        this.addAbility(new DealtDamageAttachedTriggeredAbility(
                Zone.BATTLEFIELD, new DestroyTargetEffect("destroy it"),
                false, SetTargetPointer.PERMANENT
        ).setTriggerPhrase("When enchanted creature is dealt damage, "));
    }

    private CrackedSkull(final CrackedSkull card) {
        super(card);
    }

    @Override
    public CrackedSkull copy() {
        return new CrackedSkull(this);
    }
}
