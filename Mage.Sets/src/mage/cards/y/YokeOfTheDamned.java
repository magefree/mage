package mage.cards.y;

import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DestroyAttachedToEffect;
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
 * @author jeffwadsworth
 */
public final class YokeOfTheDamned extends CardImpl {

    public YokeOfTheDamned(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget.getTargetName()));

        // When a creature dies, destroy enchanted creature.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DestroyAttachedToEffect("enchanted creature"),
                false, StaticFilters.FILTER_PERMANENT_A_CREATURE
        ).setTriggerPhrase("When a creature dies, "));
    }

    private YokeOfTheDamned(final YokeOfTheDamned card) {
        super(card);
    }

    @Override
    public YokeOfTheDamned copy() {
        return new YokeOfTheDamned(this);
    }
}
