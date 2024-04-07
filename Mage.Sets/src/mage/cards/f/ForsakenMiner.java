package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.CommittedCrimeTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ForsakenMiner extends CardImpl {

    public ForsakenMiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.SKELETON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Forsaken Miner can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever you commit a crime, you may pay {B}. If you do, return Forsaken Miner from your graveyard to the battlefield.
        this.addAbility(new CommittedCrimeTriggeredAbility(
                Zone.GRAVEYARD,
                new DoIfCostPaid(new ReturnSourceFromGraveyardToBattlefieldEffect(), new ManaCostsImpl<>("{B}")),
                false
        ));
    }

    private ForsakenMiner(final ForsakenMiner card) {
        super(card);
    }

    @Override
    public ForsakenMiner copy() {
        return new ForsakenMiner(this);
    }
}
