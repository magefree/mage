package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.hint.ConditionHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class TezzeretsSimulacrum extends CardImpl {

    private static final FilterControlledPlaneswalkerPermanent filter = new FilterControlledPlaneswalkerPermanent();

    static {
        filter.add(SubType.TEZZERET.getPredicate());
    }

    public TezzeretsSimulacrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {T}: Target opponent loses 1 life. If you control a Tezzeret planeswalker, that player loses 3 life instead.
        Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new ConditionalOneShotEffect(new LoseLifeTargetEffect(3), new LoseLifeTargetEffect(1),
                        condition,
                        "Target opponent loses 1 life. If you control a Tezzeret planeswalker, that player loses 3 life instead"), new TapSourceCost());
        ability.addTarget(new TargetOpponent());
        ability.addHint(new ConditionHint(condition, "You control a Tezzeret planeswalker"));
        this.addAbility(ability);
    }

    private TezzeretsSimulacrum(final TezzeretsSimulacrum card) {
        super(card);
    }

    @Override
    public TezzeretsSimulacrum copy() {
        return new TezzeretsSimulacrum(this);
    }
}
