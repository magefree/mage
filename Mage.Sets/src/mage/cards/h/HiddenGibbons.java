package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HiddenGibbons extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant spell");

    static {
        filter.add(CardType.INSTANT.getPredicate());
    }

    private static final Condition condition = new SourceMatchesFilterCondition("{this} is an enchantment", StaticFilters.FILTER_PERMANENT_ENCHANTMENT);

    public HiddenGibbons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent casts an instant spell, if Hidden Gibbons is an enchantment, Hidden Gibbons becomes a 4/4 Ape creature.
        this.addAbility(new SpellCastOpponentTriggeredAbility(
                new BecomesCreatureSourceEffect(new HiddenGibbonsApe(), null, Duration.WhileOnBattlefield), filter, false
        ).withInterveningIf(condition).setTriggerPhrase("When an opponent casts an instant spell, "));
    }

    private HiddenGibbons(final HiddenGibbons card) {
        super(card);
    }

    @Override
    public HiddenGibbons copy() {
        return new HiddenGibbons(this);
    }
}

class HiddenGibbonsApe extends TokenImpl {

    public HiddenGibbonsApe() {
        super("Ape", "4/4 Ape creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.APE);
        power = new MageInt(4);
        toughness = new MageInt(4);
    }

    private HiddenGibbonsApe(final HiddenGibbonsApe token) {
        super(token);
    }

    public HiddenGibbonsApe copy() {
        return new HiddenGibbonsApe(this);
    }
}
