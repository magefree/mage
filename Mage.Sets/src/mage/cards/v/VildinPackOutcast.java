package mage.cards.v;

import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class VildinPackOutcast extends TransformingDoubleFacedCard {

    public VildinPackOutcast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEREWOLF, SubType.HORROR}, "{4}{R}",
                "Dronepack Kindred",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.WEREWOLF}, ""
        );

        // Vildin-Pack Outcast
        this.getLeftHalfCard().setPT(4, 4);

        // Trample
        this.getLeftHalfCard().addAbility(TrampleAbility.getInstance());

        // {R}: Vildin-Pack Outcast gets +1/-1 until end of turn.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, -1, Duration.EndOfTurn), new ManaCostsImpl<>("{R}")));

        // {5}{R}{R}: Transform Vildin-Pack Outcast.
        this.getLeftHalfCard().addAbility(new SimpleActivatedAbility(new TransformSourceEffect(), new ManaCostsImpl<>("{5}{R}{R}")));

        // Dronepack Kindred
        this.getRightHalfCard().setPT(5, 7);

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // {1}: Dronepack Kindred gets +1/+0 until end of turn.
        this.getRightHalfCard().addAbility(new SimpleActivatedAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(1)));
    }

    private VildinPackOutcast(final VildinPackOutcast card) {
        super(card);
    }

    @Override
    public VildinPackOutcast copy() {
        return new VildinPackOutcast(this);
    }
}
