package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PermanentsEnterBattlefieldTappedEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KinjallisSunwing extends CardImpl {

    public KinjallisSunwing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Creatures your opponents control enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(new PermanentsEnterBattlefieldTappedEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ).setText("creatures your opponents control enter the battlefield tapped")));
    }

    private KinjallisSunwing(final KinjallisSunwing card) {
        super(card);
    }

    @Override
    public KinjallisSunwing copy() {
        return new KinjallisSunwing(this);
    }
}
