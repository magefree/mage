package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.GreenManaAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class BlackTomCassidy extends CardImpl {

    private static final FilterControlledCreaturePermanent filter
        = new FilterControlledCreaturePermanent(SubType.MUTANT, "another Mutant");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BlackTomCassidy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {T}: Add {G}. If you control another Mutant, you gain 1 life.
        Ability ability = new GreenManaAbility();
        ability.addEffect(new ConditionalOneShotEffect(
            new GainLifeEffect(1),
            new PermanentsOnTheBattlefieldCondition(filter),
            "If you control another Mutant, you gain 1 life."
        ));
        this.addAbility(ability);
    }

    private BlackTomCassidy(final BlackTomCassidy card) {
        super(card);
    }

    @Override
    public BlackTomCassidy copy() {
        return new BlackTomCassidy(this);
    }
}
