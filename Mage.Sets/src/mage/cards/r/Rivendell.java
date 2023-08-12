package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Rivendell extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control a legendary creature");

    static {
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    private static final Condition condition
            = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.EQUAL_TO, 0);
    private static final Condition condition2
            = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a legendary creature");

    public Rivendell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // Rivendell enters the battlefield tapped unless you control a legendary creature.
        this.addAbility(new EntersBattlefieldAbility(
                new ConditionalOneShotEffect(new TapSourceEffect(), condition, ""),
                "tapped unless you control a legendary creature"
        ).addHint(hint));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {1}{U}, {T}: Scry 2. Activate only if you control a legendary creature.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD,
                new ScryEffect(2, false),
                new ManaCostsImpl<>("{1}{U}"), condition2
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private Rivendell(final Rivendell card) {
        super(card);
    }

    @Override
    public Rivendell copy() {
        return new Rivendell(this);
    }
}
