package mage.cards.t;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class TowerWorker extends CardImpl {

    public TowerWorker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.ASSEMBLY_WORKER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Add {C}. If you control creatures named Mine Worker and Power Plant Worker, add {C}{C}{C} instead.
        this.addAbility(new DynamicManaAbility(
                Mana.ColorlessMana(1), TowerWorkerValue.instance,
                "Add {C}. If you control creatures named Mine Worker and Power Plant Worker, add {C}{C}{C} instead"
        ));
    }

    private TowerWorker(final TowerWorker card) {
        super(card);
    }

    @Override
    public TowerWorker copy() {
        return new TowerWorker(this);
    }
}

enum TowerWorkerValue implements DynamicValue {
    instance;
    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();
    private static final FilterPermanent filter2 = new FilterControlledCreaturePermanent();

    static {
        filter.add(new NamePredicate("Mine Worker"));
        filter2.add(new NamePredicate("Power Plant Worker"));
    }

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game.getBattlefield().contains(filter, sourceAbility, game, 1)
                && game.getBattlefield().contains(filter2, sourceAbility, game, 1)
                ? 3 : 1;
    }

    @Override
    public TowerWorkerValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
