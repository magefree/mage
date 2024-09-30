package mage.cards.b;

import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodstoneGoblin extends CardImpl {

    public BloodstoneGoblin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast a spell, if that spell was kicked,
        // Bloodstone Goblin gets +1/+1 and gains menace until end of turn.
        TriggeredAbility ability = new SpellCastControllerTriggeredAbility(
                new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("{this} gets +1/+1"),
                StaticFilters.FILTER_SPELL_KICKED_A, false);
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(false), Duration.EndOfTurn)
                .setText("and gains menace until end of turn. " + "<i>(It can't be blocked except by two or more creatures.)</i>"));
        ability.setTriggerPhrase("Whenever you cast a spell, if that spell was kicked, ");
        this.addAbility(ability);
    }

    private BloodstoneGoblin(final BloodstoneGoblin card) {
        super(card);
    }

    @Override
    public BloodstoneGoblin copy() {
        return new BloodstoneGoblin(this);
    }
}
