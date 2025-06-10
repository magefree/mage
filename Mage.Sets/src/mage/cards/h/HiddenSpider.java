
package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class HiddenSpider extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("creature spell with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    private static final Condition condition = new SourceMatchesFilterCondition("{this} is an enchantment", StaticFilters.FILTER_PERMANENT_ENCHANTMENT);

    public HiddenSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{G}");

        // When an opponent casts a creature spell with flying, if Hidden Spider is an enchantment, Hidden Spider becomes a 3/5 Spider creature with reach.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(
                new HiddenSpiderToken(), null, Duration.WhileOnBattlefield
        ), filter, false).withInterveningIf(condition).setTriggerPhrase("When an opponent casts a creature spell with flying, "));
    }

    private HiddenSpider(final HiddenSpider card) {
        super(card);
    }

    @Override
    public HiddenSpider copy() {
        return new HiddenSpider(this);
    }
}

class HiddenSpiderToken extends TokenImpl {

    public HiddenSpiderToken() {
        super("Spider", "3/5 Spider creature with reach");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SPIDER);
        power = new MageInt(3);
        toughness = new MageInt(5);
        this.addAbility(ReachAbility.getInstance());
    }

    private HiddenSpiderToken(final HiddenSpiderToken token) {
        super(token);
    }

    public HiddenSpiderToken copy() {
        return new HiddenSpiderToken(this);
    }
}
