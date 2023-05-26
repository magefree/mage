package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedAttachedTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.counter.AddCountersPlayersEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetArtifactPermanent;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class RelicPutrescence extends CardImpl {

    public RelicPutrescence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}");
        this.subtype.add(SubType.AURA);

        TargetPermanent auraTarget = new TargetArtifactPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted artifact becomes tapped, its controller gets a poison counter.
        this.addAbility(new BecomesTappedAttachedTriggeredAbility(new AddCountersPlayersEffect(
                CounterType.POISON.createInstance(), TargetController.CONTROLLER_ATTACHED_TO
        ), "enchanted artifact"));
    }

    public RelicPutrescence(final RelicPutrescence card) {
        super(card);
    }

    @Override
    public RelicPutrescence copy() {
        return new RelicPutrescence(this);
    }
}
