package mage.cards.v;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ViviensTalent extends CardImpl {

    public ViviensTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant planeswalker
        TargetPermanent auraTarget = new TargetPlaneswalkerPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted planeswalker has "[+1]: Look at the top four cards of your library. You may reveal a creature or land card from among them and put it into your hand. Put the rest on the bottom of your library in a random order."
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new LoyaltyAbility(new LookLibraryAndPickControllerEffect(
                        4, 1,
                        StaticFilters.FILTER_CARD_CREATURE_OR_LAND,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ), 1), AttachmentType.AURA,
                Duration.WhileOnBattlefield, null, "planeswalker"
        )));

        // Whenever a nontoken creature enters the battlefield under your control, put a loyalty counter on enchanted planeswalker.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new AddCountersAttachedEffect(
                        CounterType.LOYALTY.createInstance(), "enchanted planeswalker"
                ), StaticFilters.FILTER_CREATURE_NON_TOKEN
        ));
    }

    private ViviensTalent(final ViviensTalent card) {
        super(card);
    }

    @Override
    public ViviensTalent copy() {
        return new ViviensTalent(this);
    }
}
