
package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
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
public final class OpalCaryatid extends CardImpl {

    public OpalCaryatid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // When an opponent casts a creature spell, if Opal Caryatid is an enchantment, Opal Caryatid becomes a 2/2 Soldier creature.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalCaryatidSoldierToken(), "", Duration.WhileOnBattlefield, true, false),
                new FilterCreatureSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a creature spell, if {this} is an enchantment, {this} becomes a 2/2 Soldier creature."));
    }

    private OpalCaryatid(final OpalCaryatid card) {
        super(card);
    }

    @Override
    public OpalCaryatid copy() {
        return new OpalCaryatid(this);
    }
}

class OpalCaryatidSoldierToken extends TokenImpl {

    public OpalCaryatidSoldierToken() {
        super("Soldier", "2/2 Soldier creature");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    public OpalCaryatidSoldierToken(final OpalCaryatidSoldierToken token) {
        super(token);
    }

    public OpalCaryatidSoldierToken copy() {
        return new OpalCaryatidSoldierToken(this);
    }
}
