
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Plopman
 */
public final class RankAndFile extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("green creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public RankAndFile(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rank and File enters the battlefield, green creatures get -1/-1 until end of turn.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BoostAllEffect(-1, -1, Duration.EndOfTurn, filter, false)));
    }

    private RankAndFile(final RankAndFile card) {
        super(card);
    }

    @Override
    public RankAndFile copy() {
        return new RankAndFile(this);
    }
}
