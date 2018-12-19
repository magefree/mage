package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Aeromunculus extends CardImpl {

    public Aeromunculus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{U}");

        this.subtype.add(SubType.HOMUNCULUS);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{G}{U}: Adapt 1.
        this.addAbility(new SimpleActivatedAbility(
                new AdaptEffect(1), new ManaCostsImpl("{2}{G}{U}")
        ));
    }

    public Aeromunculus(final Aeromunculus card) {
        super(card);
    }

    @Override
    public Aeromunculus copy() {
        return new Aeromunculus(this);
    }
}
