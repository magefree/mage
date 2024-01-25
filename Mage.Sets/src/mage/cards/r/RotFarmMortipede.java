package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.LifelinkAbility;
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
public final class RotFarmMortipede extends CardImpl {

    public RotFarmMortipede(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever one or more creature cards leave your graveyard, Rot Farm Mortipede gets +1/+0 and gains menace and lifelink until end of turn.
        Ability ability = new CardsLeaveGraveyardTriggeredAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        ).setText("{this} gets +1/+0"), StaticFilters.FILTER_CARD_CREATURE);
        ability.addEffect(new GainAbilitySourceEffect(
                new MenaceAbility(false), Duration.EndOfTurn
        ).setText("and gains menace"));
        ability.addEffect(new GainAbilitySourceEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("and lifelink until end of turn"));
        this.addAbility(ability);
    }

    private RotFarmMortipede(final RotFarmMortipede card) {
        super(card);
    }

    @Override
    public RotFarmMortipede copy() {
        return new RotFarmMortipede(this);
    }
}
