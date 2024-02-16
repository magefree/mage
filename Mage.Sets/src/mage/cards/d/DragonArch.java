
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.MulticoloredPredicate;

/**
 *
 * @author LoneFox

 */
public final class DragonArch extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("a multicolored creature card");

    static {
        filter.add(MulticoloredPredicate.instance);
    }

    public DragonArch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{5}");

        // {2}, {T}: You may put a multicolored creature card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PutCardFromHandOntoBattlefieldEffect(filter),
            new ManaCostsImpl<>("{2}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private DragonArch(final DragonArch card) {
        super(card);
    }

    @Override
    public DragonArch copy() {
        return new DragonArch(this);
    }
}
