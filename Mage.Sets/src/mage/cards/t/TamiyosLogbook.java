package mage.cards.t;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public final class TamiyosLogbook extends CardImpl {

    public TamiyosLogbook(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {5}{U}, {T}: Draw a card. This ability costs {1} less to activate for each other artifact you control.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{5}{U}")
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate for each other artifact you control"));
        this.addAbility(ability.setCostAdjuster(TamiyosLogbookAdjuster.instance).addHint(TamiyosLogbookAdjuster.getHint()));
    }

    private TamiyosLogbook(final TamiyosLogbook card) {
        super(card);
    }

    @Override
    public TamiyosLogbook copy() {
        return new TamiyosLogbook(this);
    }
}

enum TamiyosLogbookAdjuster implements CostAdjuster {
    instance;
    private static final FilterPermanent filter = new FilterControlledArtifactPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Hint hint = new ValueHint(
            "Other artifacts you control", new PermanentsOnBattlefieldCount(filter)
    );

    @Override
    public void adjustCosts(Ability ability, Game game) {
        int count = game.getBattlefield().count(filter, ability.getControllerId(), ability, game);
        CardUtil.reduceCost(ability, count);
    }

    public static Hint getHint() {
        return hint;
    }
}
