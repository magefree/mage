package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.LoseLifeFirstTimeEachTurnTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.LoseLifeOpponentsYouGainLifeLostEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GontisMachinations extends CardImpl {

    public GontisMachinations(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{B}");

        // Whenever you lose life for the first time each turn, you get {E}.
        this.addAbility(new LoseLifeFirstTimeEachTurnTriggeredAbility(
                new GetEnergyCountersControllerEffect(1)));

        // Pay {E}{E}, Sacrifice Gonti's Machinations: Each opponent loses 3 life. You gain life equal to the life lost this way.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeOpponentsYouGainLifeLostEffect(3),
                new PayEnergyCost(2));
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);

    }

    private GontisMachinations(final GontisMachinations card) {
        super(card);
    }

    @Override
    public GontisMachinations copy() {
        return new GontisMachinations(this);
    }
}
