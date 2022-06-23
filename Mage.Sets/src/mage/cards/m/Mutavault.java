
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author jonubuu
 */
public final class Mutavault extends CardImpl {

    public Mutavault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {1}: Mutavault becomes a 2/2 creature with all creature types until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new MutavaultToken(), "land", Duration.EndOfTurn),
                new ManaCostsImpl<>("{1}")));
    }

    private Mutavault(final Mutavault card) {
        super(card);
    }

    @Override
    public Mutavault copy() {
        return new Mutavault(this);
    }
}

class MutavaultToken extends TokenImpl {

    public MutavaultToken() {
        super("", "2/2 creature with all creature types");
        cardType.add(CardType.CREATURE);
        subtype.setIsAllCreatureTypes(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
    public MutavaultToken(final MutavaultToken token) {
        super(token);
    }

    public MutavaultToken copy() {
        return new MutavaultToken(this);
    }
}
