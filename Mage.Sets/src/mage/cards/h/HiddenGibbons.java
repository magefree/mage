package mage.cards.h;

import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceIsEnchantmentCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HiddenGibbons extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    public HiddenGibbons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent casts an instant spell, if Hidden Gibbons is an enchantment, Hidden Gibbons becomes a 4/4 Ape creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new CreatureToken(4, 4, "4/4 Ape creature", SubType.APE),
                null, Duration.WhileOnBattlefield
        ), filter, false)
                .withInterveningIf(SourceIsEnchantmentCondition.instance)
                .withRuleTextReplacement(true)
                .setTriggerPhrase("When an opponent casts an instant spell, "));
    }

    private HiddenGibbons(final HiddenGibbons card) {
        super(card);
    }

    @Override
    public HiddenGibbons copy() {
        return new HiddenGibbons(this);
    }
}
