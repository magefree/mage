package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class MineWorker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter.add(new NamePredicate("Power Plant Worker"));
        filter2.add(new NamePredicate("Tower Worker"));
    }

    private static final Condition condition = new CompoundCondition(
            new PermanentsOnTheBattlefieldCondition(filter),
            new PermanentsOnTheBattlefieldCondition(filter2)
    );

    public MineWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // {T}: You gain 1 life. If you control creatures named Power Plant Worker and Tower Worker, you gain 3 life instead.
        this.addAbility(new SimpleActivatedAbility(new ConditionalOneShotEffect(
                new GainLifeEffect(3), new GainLifeEffect(1), condition, "you gain 1 life. " +
                "If you control creatures named Power Plant Worker and Tower Worker, you gain 3 life instead"
        ), new TapSourceCost()));
    }

    private MineWorker(final MineWorker card) {
        super(card);
    }

    @Override
    public MineWorker copy() {
        return new MineWorker(this);
    }
}
