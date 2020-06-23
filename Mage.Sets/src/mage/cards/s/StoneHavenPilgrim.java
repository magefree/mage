package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
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
public final class StoneHavenPilgrim extends CardImpl {

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT);

    public StoneHavenPilgrim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Stone Haven Pilgrim attacks, if you control an artifact or enchantment, Stone Haven Pilgrim gets +1/+1 and gains lifelink until end of turn.
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn), false
                ), condition, "Whenever {this} attacks, if you control an artifact or enchantment, " +
                "{this} gets +1/+1 and gains lifelink until end of turn."
        );
        ability.addEffect(new GainAbilitySourceEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn));
        this.addAbility(ability);
    }

    private StoneHavenPilgrim(final StoneHavenPilgrim card) {
        super(card);
    }

    @Override
    public StoneHavenPilgrim copy() {
        return new StoneHavenPilgrim(this);
    }
}
