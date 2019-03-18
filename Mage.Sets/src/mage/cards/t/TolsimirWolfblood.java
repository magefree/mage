
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.permanent.token.VojaToken;

/**
 *
 * @author Plopman
 */
public final class TolsimirWolfblood extends CardImpl {

    private static final FilterCreaturePermanent filterGreen = new FilterCreaturePermanent("green creatures you control");
    private static final FilterCreaturePermanent filterWhite = new FilterCreaturePermanent("white creatures you control");

    static {
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        filterGreen.add(new ControllerPredicate(TargetController.YOU));
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        filterWhite.add(new ControllerPredicate(TargetController.YOU));
    }

    public TolsimirWolfblood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Other green creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterGreen, true)));
        // Other white creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filterWhite, true)));
        // {tap}: Create a legendary 2/2 green and white Wolf creature token named Voja.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new VojaToken()), new TapSourceCost()));
    }

    public TolsimirWolfblood(final TolsimirWolfblood card) {
        super(card);
    }

    @Override
    public TolsimirWolfblood copy() {
        return new TolsimirWolfblood(this);
    }
}
