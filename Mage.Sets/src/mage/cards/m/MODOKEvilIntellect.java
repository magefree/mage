package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.target.common.TargetOpponent;
import mage.abilities.Ability;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MODOKEvilIntellect extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("a nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
    }

    public MODOKEvilIntellect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you draw your second card each turn, target opponent sacrifices a nontoken creature of their choice.
        Ability ability = new DrawNthCardTriggeredAbility(
            new SacrificeEffect(filter, 1, "target opponent"), false, 2
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MODOKEvilIntellect(final MODOKEvilIntellect card) {
        super(card);
    }

    @Override
    public MODOKEvilIntellect copy() {
        return new MODOKEvilIntellect(this);
    }
}
