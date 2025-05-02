package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldWithCounterEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RetrofittedTransmogrant extends CardImpl {

    public RetrofittedTransmogrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {3}{B}: Return Retrofitted Transmogrant from your graveyard to the battlefield tapped with two +1/+1 counters on it.
        this.addAbility(new SimpleActivatedAbility(
                Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldWithCounterEffect(
                        CounterType.P1P1.createInstance(2), true
                ),
                new ManaCostsImpl<>("{3}{B}")
        ));
    }

    private RetrofittedTransmogrant(final RetrofittedTransmogrant card) {
        super(card);
    }

    @Override
    public RetrofittedTransmogrant copy() {
        return new RetrofittedTransmogrant(this);
    }
}
