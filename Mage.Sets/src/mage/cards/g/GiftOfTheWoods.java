
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BlocksOrBecomesBlockedSourceTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Ketsuban
 */
public final class GiftOfTheWoods extends CardImpl {

    public GiftOfTheWoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.ENCHANTMENT }, "{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Benefit));
        this.getSpellAbility().addTarget(auraTarget);
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Whenever enchanted creature blocks or becomes blocked, it gets +0/+3 until
        // end of turn and you gain 1 life.
        Ability ability2 = new BlocksOrBecomesBlockedSourceTriggeredAbility(
                new BoostEnchantedEffect(0, 3, Duration.EndOfTurn), false);
        ability2.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability2);
    }

    private GiftOfTheWoods(final GiftOfTheWoods card) {
        super(card);
    }

    @Override
    public GiftOfTheWoods copy() {
        return new GiftOfTheWoods(this);
    }
}
