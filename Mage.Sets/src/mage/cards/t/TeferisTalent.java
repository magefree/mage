package mage.cards.t;

import mage.abilities.LoyaltyAbility;
import mage.abilities.common.DrawCardControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GetEmblemEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.command.emblems.TeferisTalentEmblem;
import mage.target.TargetPermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TeferisTalent extends CardImpl {

    public TeferisTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant planeswalker
        TargetPermanent auraTarget = new TargetPlaneswalkerPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted planeswalker has "[-12]: You get an emblem with 'You may activate loyalty abilities of planeswalkers you control on any player's turn any time you could cast an instant.'"
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(
                new LoyaltyAbility(new GetEmblemEffect(new TeferisTalentEmblem()), -12),
                AttachmentType.AURA, Duration.WhileOnBattlefield, null, "planeswalker"
        )));

        // Whenever you draw a card, put a loyalty counter on enchanted planeswalker.
        this.addAbility(new DrawCardControllerTriggeredAbility(new AddCountersAttachedEffect(
                CounterType.LOYALTY.createInstance(), "enchanted planeswalker"
        ), false));
    }

    private TeferisTalent(final TeferisTalent card) {
        super(card);
    }

    @Override
    public TeferisTalent copy() {
        return new TeferisTalent(this);
    }
}
