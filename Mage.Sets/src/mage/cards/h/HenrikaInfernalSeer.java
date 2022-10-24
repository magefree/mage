package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HenrikaInfernalSeer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("each creature you control with flying, deathtouch, and/or lifelink");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(Predicates.or(
                new AbilityPredicate(FlyingAbility.class),
                new AbilityPredicate(DeathtouchAbility.class),
                new AbilityPredicate(LifelinkAbility.class)
        ));
    }

    public HenrikaInfernalSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // {1}{B}{B}: Each creature you control with flying, deathtouch, and/or lifelink gets +1/+0 until end of turn.
        this.addAbility(new SimpleActivatedAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn, filter, false
        ), new ManaCostsImpl<>("{1}{B}{B}")));
    }

    private HenrikaInfernalSeer(final HenrikaInfernalSeer card) {
        super(card);
    }

    @Override
    public HenrikaInfernalSeer copy() {
        return new HenrikaInfernalSeer(this);
    }
}
