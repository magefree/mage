package mage.cards.s;

import java.util.UUID;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SuperIntelligence extends CardImpl {

    public SuperIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // At the beginning of the upkeep of enchanted creature's controller, that player draws a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
            TargetController.CONTROLLER_ATTACHED_TO,
            new DrawCardTargetEffect(1),
            false
        ));
    }

    private SuperIntelligence(final SuperIntelligence card) {
        super(card);
    }

    @Override
    public SuperIntelligence copy() {
        return new SuperIntelligence(this);
    }
}
