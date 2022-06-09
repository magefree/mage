package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.StaticFilters;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author jeffwadsworth
 */
public final class VeiledApparition extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell();

    public VeiledApparition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        

        // When an opponent casts a spell, if Veiled Apparition is an enchantment, Veiled Apparition becomes a 3/3 Illusion creature with flying and "At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}."
         TriggeredAbility ability = new SpellCastOpponentTriggeredAbility(new BecomesCreatureSourceEffect(new VeilApparitionToken(), "", Duration.WhileOnBattlefield, true, false),
                filter, false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, new SourceMatchesFilterCondition(StaticFilters.FILTER_PERMANENT_ENCHANTMENT),
                "Whenever an opponent casts a spell, if Veiled Apparition is an enchantment, Veiled Apparition becomes a 3/3 Illusion creature with flying and \"At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}."));
        
    }

    private VeiledApparition(final VeiledApparition card) {
        super(card);
    }

    @Override
    public VeiledApparition copy() {
        return new VeiledApparition(this);
    }
}

class VeilApparitionToken extends TokenImpl {

    public VeilApparitionToken() {
        super("Illusion", "3/3 Illusion creature with flying and \"At the beginning of your upkeep, sacrifice Veiled Apparition unless you pay {1}{U}.");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.ILLUSION);
        power = new MageInt(3);
        toughness = new MageInt(3);
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DoUnlessControllerPaysEffect(new SacrificeSourceEffect(), new ManaCostsImpl<>("{1}{U}")), TargetController.YOU, false);
        this.addAbility(ability);
    }

    public VeilApparitionToken(final VeilApparitionToken token) {
        super(token);
    }

    public VeilApparitionToken copy() {
        return new VeilApparitionToken(this);
    }
}