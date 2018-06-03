
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterPlaneswalkerPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class TezzeretsSimulacrum extends CardImpl {

    public TezzeretsSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Target opponent loses 1 life. If you control a Tezzeret planeswalker, that player loses 3 life instead.
        FilterPlaneswalkerPermanent filter = new FilterPlaneswalkerPermanent();
        filter.add(new SubtypePredicate(SubType.TEZZERET));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ConditionalOneShotEffect(new LoseLifeTargetEffect(3), new LoseLifeTargetEffect(1),
                        new PermanentsOnTheBattlefieldCondition(filter),
                        "Target opponent loses 1 life. If you control a Tezzeret planeswalker, that player loses 3 life instead"), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public TezzeretsSimulacrum(final TezzeretsSimulacrum card) {
        super(card);
    }

    @Override
    public TezzeretsSimulacrum copy() {
        return new TezzeretsSimulacrum(this);
    }
}
