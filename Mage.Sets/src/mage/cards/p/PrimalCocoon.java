package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksOrBlocksAttachedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class PrimalCocoon extends CardImpl {

    public PrimalCocoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // At the beginning of your upkeep, put a +1/+1 counter on enchanted creature.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersAttachedEffect(
                CounterType.P1P1.createInstance(), "enchanted creature"
        ), TargetController.YOU, false));

        // When enchanted creature attacks or blocks, sacrifice Primal Cocoon.
        this.addAbility(new AttacksOrBlocksAttachedTriggeredAbility(
                new SacrificeSourceEffect(), AttachmentType.AURA)
                .setTriggerPhrase("When enchanted creature attacks or blocks, "));

    }

    private PrimalCocoon(final PrimalCocoon card) {
        super(card);
    }

    @Override
    public PrimalCocoon copy() {
        return new PrimalCocoon(this);
    }
}
