
package mage.cards.h;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author LoneFox
 *
 */
public final class HiddenSpider extends CardImpl {

    private static final FilterCreatureSpell filter = new FilterCreatureSpell("creature spell with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
    }

    public HiddenSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{G}");

        // When an opponent casts a creature spell with flying, if Hidden Spider is an enchantment, Hidden Spider becomes a 3/5 Spider creature with reach.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new HiddenSpiderToken(), "", Duration.WhileOnBattlefield, true, false),
                filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a creature spell with flying, if {this} is an enchantment, {this} becomes a 3/5 Spider creature with reach."));
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
    public HiddenSpiderToken(final HiddenSpiderToken token) {
        super(token);
    }

    public HiddenSpiderToken copy() {
        return new HiddenSpiderToken(this);
    }
}
