package mage.cards.g;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DisturbAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Galedrifter extends CardImpl {

    public Galedrifter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HIPPOGRIFF);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);
        this.secondSideCardClazz = mage.cards.w.Waildrifter.class;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Disturb {4}{U}
        this.addAbility(new TransformAbility());
        this.addAbility(new DisturbAbility(new ManaCostsImpl<>("{4}{U}")));
    }

    private Galedrifter(final Galedrifter card) {
        super(card);
    }

    @Override
    public Galedrifter copy() {
        return new Galedrifter(this);
    }
}
