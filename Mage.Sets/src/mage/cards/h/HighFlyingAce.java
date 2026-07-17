package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HighFlyingAce extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("target creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public HighFlyingAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.MUTANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {3}{W}: Target creature without flying gains flying until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance()), new ManaCostsImpl<>("{3}{W}"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private HighFlyingAce(final HighFlyingAce card) {
        super(card);
    }

    @Override
    public HighFlyingAce copy() {
        return new HighFlyingAce(this);
    }
}
