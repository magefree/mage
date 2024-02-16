package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.PrototypeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallajiDragonEngine extends CardImpl {

    public FallajiDragonEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{8}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Prototype {2}{R} -- 1/3
        this.addAbility(new PrototypeAbility(this, "{2}{R}", 1, 3));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}: Fallaji Dragon Engine gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new GenericManaCost(2)
        ));
    }

    private FallajiDragonEngine(final FallajiDragonEngine card) {
        super(card);
    }

    @Override
    public FallajiDragonEngine copy() {
        return new FallajiDragonEngine(this);
    }
}
