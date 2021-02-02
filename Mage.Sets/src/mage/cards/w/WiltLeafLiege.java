
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DiscardOntoBattlefieldEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author Plopman
 */
public final class WiltLeafLiege extends CardImpl {

    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures you control");
    private static final FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("white creatures you control");
    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterGreen.add(TargetController.YOU.getControllerPredicate());
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterWhite.add(TargetController.YOU.getControllerPredicate());
    }

    public WiltLeafLiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G/W}{G/W}{G/W}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
        // Other white creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterWhite, true)));
        // If a spell or ability an opponent controls causes you to discard Wilt-Leaf Liege, put it onto the battlefield instead of putting it into your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.HAND, new DiscardOntoBattlefieldEffect()));
    }

    private WiltLeafLiege(final WiltLeafLiege card) {
        super(card);
    }

    @Override
    public WiltLeafLiege copy() {
        return new WiltLeafLiege(this);
    }
}
