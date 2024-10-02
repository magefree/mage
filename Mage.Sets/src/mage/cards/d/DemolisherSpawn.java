package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.hint.common.CardTypesInGraveyardHint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
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
public final class DemolisherSpawn extends CardImpl {

    public DemolisherSpawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}{G}{G}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Delirium -- Whenever Demolisher Spawn attacks, if there are four or more card types among cards in your graveyard, other attacking creatures get +4/+4 until end of turn.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new BoostAllEffect(
                        4, 4, Duration.EndOfTurn,
                        StaticFilters.FILTER_ATTACKING_CREATURES, true
                )), DeliriumCondition.instance, "Whenever {this} attacks, if there are four or more " +
                "card types among cards in your graveyard, other attacking creatures get +4/+4 until end of turn."
        ).setAbilityWord(AbilityWord.DELIRIUM).addHint(CardTypesInGraveyardHint.YOU));
    }

    private DemolisherSpawn(final DemolisherSpawn card) {
        super(card);
    }

    @Override
    public DemolisherSpawn copy() {
        return new DemolisherSpawn(this);
    }
}
