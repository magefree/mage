package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ShivanWurm extends CardImpl {

    static final private FilterControlledPermanent filter
            = new FilterControlledCreaturePermanent("red or green creature you control");

    static {
        filter.add(Predicates.or(
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.GREEN)
        ));
    }

    public ShivanWurm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");
        this.subtype.add(SubType.WURM);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Shivan Wurm enters the battlefield, return a red or green creature you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new ReturnToHandChosenControlledPermanentEffect(filter), false
        ));
    }

    private ShivanWurm(final ShivanWurm card) {
        super(card);
    }

    @Override
    public ShivanWurm copy() {
        return new ShivanWurm(this);
    }
}
