
package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreatureSpell;
import mage.game.permanent.token.TokenImpl;

import java.util.UUID;

/**
 *
 * @author LoneFox
 *
 */
public final class OpalGargoyle extends CardImpl {

    public OpalGargoyle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{W}");

        // When an opponent casts a creature spell, if Opal Gargoyle is an enchantment, Opal Gargoyle becomes a 2/2 Gargoyle creature with flying.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalGargoyleToken(), null, Duration.WhileOnBattlefield),
                new FilterCreatureSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a creature spell, if {this} is an enchantment, {this} becomes a 2/2 Gargoyle creature with flying."));
    }

    private OpalGargoyle(final OpalGargoyle card) {
        super(card);
    }

    @Override
    public OpalGargoyle copy() {
        return new OpalGargoyle(this);
    }
}

class OpalGargoyleToken extends TokenImpl {

    public OpalGargoyleToken() {
        super("Gargoyle", "2/2 Gargoyle creature with flying");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());
    }
    public OpalGargoyleToken(final OpalGargoyleToken token) {
        super(token);
    }

    public OpalGargoyleToken copy() {
        return new OpalGargoyleToken(this);
    }
}
