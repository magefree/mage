package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class ElderArthurMaxson extends CardImpl {

    public ElderArthurMaxson(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // Creature tokens you control have training.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new TrainingAbility(), Duration.WhileOnBattlefield, StaticFilters.FILTER_CREATURE_TOKENS
        )));

        // Blind Betrayal -- Sacrifice another creature: Elder Arthur Maxson gains indestructible until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new GainAbilitySourceEffect(
                        IndestructibleAbility.getInstance(), Duration.EndOfTurn
                ), new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        ).withFlavorWord("Blind Betrayal"));
    }

    private ElderArthurMaxson(final ElderArthurMaxson card) {
        super(card);
    }

    @Override
    public ElderArthurMaxson copy() {
        return new ElderArthurMaxson(this);
    }
}
