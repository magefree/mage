package mage.cards.h;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.WardAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author weirddan455
 */
public final class HallOfStormGiants extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1, true);

    public HallOfStormGiants(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // If you control two or more other lands, Hall of Storm Giants enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(
                new TapSourceEffect(), condition, "If you control two or more other lands, {this} enters the battlefield tapped.", null
        ));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {5}{U}: Until end of turn, Hall of Storm Giants becomes a 7/7 blue Giant creature with ward {3}. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(7, 7, "7/7 blue Giant creature with ward {3}")
                        .withColor("U")
                        .withSubType(SubType.GIANT)
                        .withAbility(new WardAbility(new GenericManaCost(3))),
                "land", Duration.EndOfTurn).setText(
                        "Until end of turn, Hall of Storm Giants becomes a 7/7 blue Giant creature with ward {3}. " +
                                "It's still a land. " +
                                "<i>(Whenever it becomes the target of a spell or ability an opponent controls, " +
                                "counter it unless that player pays {3}.)</i>"),
                new ManaCostsImpl<>("{5}{U}")));
    }

    private HallOfStormGiants(final HallOfStormGiants card) {
        super(card);
    }

    @Override
    public HallOfStormGiants copy() {
        return new HallOfStormGiants(this);
    }
}
