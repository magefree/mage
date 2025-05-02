package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.ControlYourCommanderCondition;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LoyalUnicorn extends CardImpl {

    public LoyalUnicorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.UNICORN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Lieutenant — At the beginning of combat on your turn, if you control your commander, prevent all combat damage that would be dealt to creatures you control this turn. Other creatures you control gain vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new PreventAllDamageToAllEffect(
                Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_CREATURES, true
        )).withInterveningIf(ControlYourCommanderCondition.instance);
        ability.addEffect(new GainAbilityAllEffect(
                VigilanceAbility.getInstance(), Duration.EndOfTurn,
                StaticFilters.FILTER_CONTROLLED_CREATURES, true
        ));
        this.addAbility(ability.setAbilityWord(AbilityWord.LIEUTENANT));
    }

    private LoyalUnicorn(final LoyalUnicorn card) {
        super(card);
    }

    @Override
    public LoyalUnicorn copy() {
        return new LoyalUnicorn(this);
    }
}
