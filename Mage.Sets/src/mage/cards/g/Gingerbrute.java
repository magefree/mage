package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.token.FoodAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Gingerbrute extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("except by creatures with haste");

    static {
        filter.add(Predicates.not(new AbilityPredicate(HasteAbility.class)));
    }

    public Gingerbrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.FOOD);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}: Gingerbrute can't be blocked this turn except by creatures with haste.
        this.addAbility(new SimpleActivatedAbility(
                new CantBeBlockedByCreaturesSourceEffect(filter, Duration.EndOfTurn), new GenericManaCost(1)
        ));

        // {2}, {T}, Sacrifice Gingerbrute: You gain 3 life.
        this.addAbility(new FoodAbility(true));
    }

    private Gingerbrute(final Gingerbrute card) {
        super(card);
    }

    @Override
    public Gingerbrute copy() {
        return new Gingerbrute(this);
    }
}
