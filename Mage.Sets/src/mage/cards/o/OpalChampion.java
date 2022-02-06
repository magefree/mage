
package mage.cards.o;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
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
public final class OpalChampion extends CardImpl {

    public OpalChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}");

        // When an opponent casts a creature spell, if Opal Champion is an enchantment, Opal Champion becomes a 3/3 Knight creature with first strike.
        TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new OpalChampionKnight(), "", Duration.WhileOnBattlefield, true, false),
                new FilterCreatureSpell(), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a creature spell, if {this} is an enchantment, {this} becomes a 3/3 Knight creature with first strike."));
    }

    private OpalChampion(final OpalChampion card) {
        super(card);
    }

    @Override
    public OpalChampion copy() {
        return new OpalChampion(this);
    }
}

class OpalChampionKnight extends TokenImpl {

    public OpalChampionKnight() {
        super("Knight", "3/3 Knight creature with first strike");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FirstStrikeAbility.getInstance());
    }
    public OpalChampionKnight(final OpalChampionKnight token) {
        super(token);
    }

    public OpalChampionKnight copy() {
        return new OpalChampionKnight(this);
    }
}
