
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;

/**
 *
 * @author Loki
 */
public final class ScrollOfAvacyn extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(SubType.ANGEL.getPredicate());
    }

    public ScrollOfAvacyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {1}, Sacrifice Scroll of Avacyn: Draw a card. If you control an Angel, you gain 5 life.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), new GenericManaCost(1));
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(new GainLifeEffect(5), new PermanentsOnTheBattlefieldCondition(filter), "If you control an Angel, you gain 5 life"));
        this.addAbility(ability);
    }

    private ScrollOfAvacyn(final ScrollOfAvacyn card) {
        super(card);
    }

    @Override
    public ScrollOfAvacyn copy() {
        return new ScrollOfAvacyn(this);
    }
}
