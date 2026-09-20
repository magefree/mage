package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.token.FoodAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author muz
 */
public final class GingerbruteToken extends TokenImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("except by creatures with haste");

    static {
        filter.add(Predicates.not(new AbilityPredicate(HasteAbility.class)));
    }

    public GingerbruteToken() {
        super("Gingerbrute", "Gingerbrute token");
        manaCost = new ManaCostsImpl<>("{1}");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FOOD);
        subtype.add(SubType.GOLEM);
        power = new MageInt(1);
        toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {1}: Gingerbrute can't be blocked this turn except by creatures with haste.
        this.addAbility(new SimpleActivatedAbility(
            new CantBeBlockedByCreaturesSourceEffect(filter, Duration.EndOfTurn),
            new GenericManaCost(1)
        ));

        // {2}, {T}, Sacrifice Gingerbrute: You gain 3 life.
        this.addAbility(new FoodAbility());
    }

    private GingerbruteToken(final GingerbruteToken token) {
        super(token);
    }

    @Override
    public GingerbruteToken copy() {
        return new GingerbruteToken(this);
    }
}
