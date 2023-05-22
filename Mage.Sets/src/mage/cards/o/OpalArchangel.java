
package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
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
public final class OpalArchangel extends CardImpl {

    public OpalArchangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{W}");

        // When an opponent casts a creature spell, if Opal Archangel is an enchantment, Opal Archangel becomes a 5/5 Angel creature with flying and vigilance.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalArchangelToken(), null, Duration.WhileOnBattlefield),
                new FilterCreatureSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a creature spell, if {this} is an enchantment, {this} becomes a 5/5 Angel creature with flying and vigilance."));
    }

    private OpalArchangel(final OpalArchangel card) {
        super(card);
    }

    @Override
    public OpalArchangel copy() {
        return new OpalArchangel(this);
    }
}

class OpalArchangelToken extends TokenImpl {

    public OpalArchangelToken() {
        super("Angel", "5/5 Angel creature with flying and vigilance");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ANGEL);
        power = new MageInt(5);
        toughness = new MageInt(5);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }
    public OpalArchangelToken(final OpalArchangelToken token) {
        super(token);
    }

    public OpalArchangelToken copy() {
        return new OpalArchangelToken(this);
    }
}
