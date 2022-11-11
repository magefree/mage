package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.UnearthAbility;
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
 * @author PurpleCrowbar
 */
public final class YotianFrontliner extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another target creature you control");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public YotianFrontliner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Yotian Frontliner attacks, another target creature you control gets +1/+1 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(
                1, 1, Duration.EndOfTurn
        ), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Unearth {W}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{W}")));

    }

    private YotianFrontliner(final YotianFrontliner card) {
        super(card);
    }

    @Override
    public YotianFrontliner copy() {
        return new YotianFrontliner(this);
    }
}
