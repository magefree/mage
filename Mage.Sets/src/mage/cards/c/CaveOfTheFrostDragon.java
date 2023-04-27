package mage.cards.c;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.mana.WhiteManaAbility;
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
public final class CaveOfTheFrostDragon extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1, true);

    public CaveOfTheFrostDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // If you control two or more other lands, Cave of the Frost Dragon enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(
                new TapSourceEffect(), condition, "If you control two or more other lands, {this} enters the battlefield tapped.", null
        ));

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // {4}{W}: Cave of the Frost Dragon becomes a 3/4 white Dragon creature with flying until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 4, "3/4 white Dragon creature with flying")
                        .withColor("W")
                        .withSubType(SubType.DRAGON)
                        .withAbility(FlyingAbility.getInstance()),
                "land", Duration.EndOfTurn), new ManaCostsImpl<>("{4}{W}")));
    }

    private CaveOfTheFrostDragon(final CaveOfTheFrostDragon card) {
        super(card);
    }

    @Override
    public CaveOfTheFrostDragon copy() {
        return new CaveOfTheFrostDragon(this);
    }
}
