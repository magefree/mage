package mage.cards.h;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HiddenSpider extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("creature spell with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public HiddenSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent casts a creature spell with flying, if Hidden Spider is an enchantment, Hidden Spider becomes a 3/5 Spider creature with reach.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(
                        3, 5, "3/5 Spider creature with reach", SubType.SPIDER
                ).withAbility(ReachAbility.getInstance()), null, Duration.WhileOnBattlefield
        ), filter, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts a creature spell with flying, "));
    }

    private HiddenSpider(final HiddenSpider card) {
        super(card);
    }

    @Override
    public HiddenSpider copy() {
        return new HiddenSpider(this);
    }
}
