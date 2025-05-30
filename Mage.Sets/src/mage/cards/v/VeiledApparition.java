package mage.cards.v;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
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
 *
 * @author jeffwadsworth
 */
public final class VeiledApparition extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell();

    public VeiledApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // When an opponent casts a spell, if Veiled Apparition is an enchantment, Veiled Apparition becomes a 3/3 Illusion creature with flying and "At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}."
         TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new VeiledApparitionToken(), null, Duration.WhileOnBattlefield),
                filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "When an opponent casts a spell, if {this} is an enchantment, {this} becomes a 3/3 Illusion creature with flying and \"At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}.\""));
        
    }

    private VeiledApparition(final VeiledApparition card) {
        super(card);
    }

    @Override
    public VeiledApparition copy() {
        return new VeiledApparition(this);
    }
}

class VeiledApparitionToken extends TokenImpl {

    VeiledApparitionToken() {
        super("Illusion", "3/3 Illusion creature with flying and \"At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}.");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new SacrificeSourceUnlessPaysEffect(new ManaCostsImpl<>("{1}{U}"))
        ));
    }

    private VeiledApparitionToken(final VeiledApparitionToken token) {
        super(token);
    }

    public VeiledApparitionToken copy() {
        return new VeiledApparitionToken(this);
    }
}
