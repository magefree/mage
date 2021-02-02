package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.BoostOpponentsEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author TheElk801
 */
public final class PlagueMare extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("white creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.WHITE));
    }

    public PlagueMare(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HORSE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Plague Mare can't be blocked by white creatures.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new CantBeBlockedByCreaturesSourceEffect(
                        filter, Duration.WhileOnBattlefield
                )
        ));

        // When Plague Mare enters the battlefield, creatures your opponents control get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new BoostOpponentsEffect(-1, -1, Duration.EndOfTurn)
        ));
    }

    private PlagueMare(final PlagueMare card) {
        super(card);
    }

    @Override
    public PlagueMare copy() {
        return new PlagueMare(this);
    }
}
