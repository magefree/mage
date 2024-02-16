package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TidepoolTurtle extends CardImpl {

    public TidepoolTurtle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // {2}{U}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(new ScryEffect(1), new ManaCostsImpl<>("{2}{U}")));
    }

    private TidepoolTurtle(final TidepoolTurtle card) {
        super(card);
    }

    @Override
    public TidepoolTurtle copy() {
        return new TidepoolTurtle(this);
    }
}
