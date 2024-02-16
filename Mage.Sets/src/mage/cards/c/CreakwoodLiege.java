
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.permanent.token.BlackGreenWormToken;

/**
 *
 * @author anonymous
 */
public final class CreakwoodLiege extends CardImpl {

    private static final FilterCreaturePermanent filterBlackCreature = new FilterCreaturePermanent("black creatures");
    private static final FilterCreaturePermanent filterGreenCreature = new FilterCreaturePermanent("green creatures");

    static {
        filterBlackCreature.add(new ColorPredicate(ObjectColor.BLACK));
        filterGreenCreature.add(new ColorPredicate(ObjectColor.GREEN));
    }

    public CreakwoodLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B/G}{B/G}{B/G}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Other black creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterBlackCreature, true)));
        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterGreenCreature, true)));
        // At the beginning of your upkeep, you may create a 1/1 black and green Worm creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new CreateTokenEffect(new BlackGreenWormToken(), 1), TargetController.YOU, true));
    }

    private CreakwoodLiege(final CreakwoodLiege card) {
        super(card);
    }

    @Override
    public CreakwoodLiege copy() {
        return new CreakwoodLiege(this);
    }
}
