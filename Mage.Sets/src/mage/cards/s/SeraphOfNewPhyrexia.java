package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeraphOfNewPhyrexia extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("another creature or artifact");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.ARTIFACT.getPredicate()
        ));
    }

    public SeraphOfNewPhyrexia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setWhite(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Seraph of New Phyrexia attacks, you may sacrifice another creature or artifact. If you do, Seraph of New Phyrexia gets +2/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new DoIfCostPaid(
                new BoostSourceEffect(2, 1, Duration.EndOfTurn), new SacrificeTargetCost(filter)
        )));
    }

    private SeraphOfNewPhyrexia(final SeraphOfNewPhyrexia card) {
        super(card);
    }

    @Override
    public SeraphOfNewPhyrexia copy() {
        return new SeraphOfNewPhyrexia(this);
    }
}
