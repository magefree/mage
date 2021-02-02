
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Loki
 */
public final class ScrollOfGriselbrand extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.DEMON.getPredicate());
    }

    public ScrollOfGriselbrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {1}, Sacrifice Scroll of Griselbrand: Target opponent discards a card. If you control a Demon, that player loses 3 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DiscardTargetEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new LoseLifeTargetEffect(3), new PermanentsOnTheBattlefieldCondition(filter), "If you control a Demon, that player loses 3 life"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private ScrollOfGriselbrand(final ScrollOfGriselbrand card) {
        super(card);
    }

    @Override
    public ScrollOfGriselbrand copy() {
        return new ScrollOfGriselbrand(this);
    }
}
