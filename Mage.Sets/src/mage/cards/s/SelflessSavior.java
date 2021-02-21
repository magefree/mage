package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SelflessSavior extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public SelflessSavior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Selfless Savior: Another target creature you control gains indestructible until end of turn.
        Ability ability = new SimpleActivatedAbility(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ), new SacrificeSourceCost());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private SelflessSavior(final SelflessSavior card) {
        super(card);
    }

    @Override
    public SelflessSavior copy() {
        return new SelflessSavior(this);
    }
}
