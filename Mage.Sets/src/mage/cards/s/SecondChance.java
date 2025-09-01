package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.condition.common.FatefulHourCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Plopman
 */
public final class SecondChance extends CardImpl {

    public SecondChance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, if you have 5 or less life, sacrifice Second Chance and take an extra turn after this one.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect()).withInterveningIf(FatefulHourCondition.instance);
        ability.addEffect(new AddExtraTurnControllerEffect().concatBy("and"));
        this.addAbility(ability);
    }

    private SecondChance(final SecondChance card) {
        super(card);
    }

    @Override
    public SecondChance copy() {
        return new SecondChance(this);
    }


}
