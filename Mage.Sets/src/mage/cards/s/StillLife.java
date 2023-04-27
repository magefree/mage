
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author LoneFox

 */
public final class StillLife extends CardImpl {

    public StillLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{G}{G}");

        // {G}{G}: Still Life becomes a 4/3 Centaur creature until end of turn. It's still an enchantment.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(new StillLifeCentaur(), "", Duration.EndOfTurn), new ManaCostsImpl<>("{G}{G}")));
    }

    private StillLife(final StillLife card) {
        super(card);
    }

    @Override
    public StillLife copy() {
        return new StillLife(this);
    }
}

class StillLifeCentaur extends TokenImpl {

    public StillLifeCentaur() {
        super("Centaur", "4/3 Centaur creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CENTAUR);
        power = new MageInt(4);
        toughness = new MageInt(3);
    }
    public StillLifeCentaur(final StillLifeCentaur token) {
        super(token);
    }

    public StillLifeCentaur copy() {
        return new StillLifeCentaur(this);
    }
}
