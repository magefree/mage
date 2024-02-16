package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.SquadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GaryClone extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("each creature you control named Gary Clone");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(new NamePredicate("Gary Clone"));
    }

    public GaryClone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CITIZEN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Squad {2}
        this.addAbility(new SquadAbility());

        // Whenever Gary Clone attacks, each creature you control named Gary Clone gets +1/+0 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(
                1, 0, Duration.EndOfTurn, filter, false
        )));
    }

    private GaryClone(final GaryClone card) {
        super(card);
    }

    @Override
    public GaryClone copy() {
        return new GaryClone(this);
    }
}
