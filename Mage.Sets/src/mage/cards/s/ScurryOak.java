package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.OneOrMoreCountersAddedTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SquirrelToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScurryOak extends CardImpl {

    public ScurryOak(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Whenever one or more +1/+1 counters are put on Scurry Oak, you may create a 1/1 green Squirrel creature token.
        this.addAbility(new OneOrMoreCountersAddedTriggeredAbility(
                new CreateTokenEffect(new SquirrelToken()), true
        ));
    }

    private ScurryOak(final ScurryOak card) {
        super(card);
    }

    @Override
    public ScurryOak copy() {
        return new ScurryOak(this);
    }
}
