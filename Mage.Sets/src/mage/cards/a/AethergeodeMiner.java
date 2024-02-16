package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class AethergeodeMiner extends CardImpl {

    public AethergeodeMiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Aethergeode Miner attacks, you get {E}{E}.
        this.addAbility(new AttacksTriggeredAbility(new GetEnergyCountersControllerEffect(2), false));

        // Pay {E}{E}: Exile Aethergeode Miner, then return it to the battlefield under its owner's control.
        this.addAbility(new SimpleActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD), new PayEnergyCost(2)
        ));
    }

    private AethergeodeMiner(final AethergeodeMiner card) {
        super(card);
    }

    @Override
    public AethergeodeMiner copy() {
        return new AethergeodeMiner(this);
    }
}
