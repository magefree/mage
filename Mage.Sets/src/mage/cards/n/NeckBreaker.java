package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.WerewolfBackTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class NeckBreaker extends CardImpl {

    public NeckBreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        this.color.setRed(true);

        // this card is the second face of double-faced card
        this.nightCard = true;

        // Attacking creatures you control get +1/+0 and have trample.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                TrampleAbility.getInstance(), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_ATTACKING_CREATURES
        ).setText("and have trample"));
        this.addAbility(ability);

        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Neck Breaker.
        this.addAbility(new WerewolfBackTriggeredAbility());
    }

    private NeckBreaker(final NeckBreaker card) {
        super(card);
    }

    @Override
    public NeckBreaker copy() {
        return new NeckBreaker(this);
    }
}
