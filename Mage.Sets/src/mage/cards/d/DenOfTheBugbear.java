package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.custom.CreatureToken;

/**
 *
 * @author weirddan455
 */
public final class DenOfTheBugbear extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.MORE_THAN, 1, true);

    public DenOfTheBugbear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // If you control two or more other lands, Den of the Bugbear enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldAbility(
                new TapSourceEffect(), condition, "If you control two or more other lands, {this} enters the battlefield tapped.", null
        ));

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());

        // {3}{R}: Until end of turn, Den of the Bugbear becomes a 3/2 red Goblin creature with
        // "Whenever this creature attacks, create a 1/1 red Goblin creature token that's tapped and attacking." It's still a land.
        Ability ability = new AttacksTriggeredAbility(
                new CreateTokenEffect(new GoblinToken(), 1, true, true), false,
                "Whenever this creature attacks, create a 1/1 red Goblin creature token that's tapped and attacking."
        );
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new BecomesCreatureSourceEffect(
                new CreatureToken(3, 2, "3/2 red Goblin creature with \"Whenever this creature attacks, create a 1/1 red Goblin creature token that's tapped and attacking.\"")
                        .withColor("R")
                        .withSubType(SubType.GOBLIN)
                        .withAbility(ability),
                CardType.LAND, Duration.EndOfTurn).withDurationRuleAtStart(true), new ManaCostsImpl<>("{3}{R}")));
    }

    private DenOfTheBugbear(final DenOfTheBugbear card) {
        super(card);
    }

    @Override
    public DenOfTheBugbear copy() {
        return new DenOfTheBugbear(this);
    }
}
