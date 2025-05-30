package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.CrewSaddleIncreasedPowerAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DeathlessPilot extends CardImpl {

    public DeathlessPilot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // This creature saddles Mounts and crews Vehicles as though its power were 2 greater.
        this.addAbility(new CrewSaddleIncreasedPowerAbility());

        // {3}{B}: Return this card from your graveyard to your hand.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(), new ManaCostsImpl<>("{3}{B}")
        ));
    }

    private DeathlessPilot(final DeathlessPilot card) {
        super(card);
    }

    @Override
    public DeathlessPilot copy() {
        return new DeathlessPilot(this);
    }
}
