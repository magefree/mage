
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPermanentCard;

/**
 *
 * @author LoneFox
 */
public final class Didgeridoo extends CardImpl {

    private static final FilterPermanentCard filter = new FilterPermanentCard("a Minotaur permanent card");

    static {
        filter.add(SubType.MINOTAUR.getPredicate());
    }

    public Didgeridoo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {3}: You may put a Minotaur permanent card from your hand onto the battlefield.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter), new ManaCostsImpl<>("{3}")));
    }

    private Didgeridoo(final Didgeridoo card) {
        super(card);
    }

    @Override
    public Didgeridoo copy() {
        return new Didgeridoo(this);
    }
}
