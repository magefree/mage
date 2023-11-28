package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 * @author ilcartographer
 */
public final class FaerieNoble extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Faerie creatures");

    static {
        filter.add(SubType.FAERIE.getPredicate());
    }

    public FaerieNoble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.FAERIE, SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Other Faerie creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, filter, true)));
        // {tap}: Other Faerie creatures you control get +1/+0 until end of turn.
        Effect effect = new BoostControlledEffect(1, 0, Duration.EndOfTurn, filter, true);
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new TapSourceCost());
        this.addAbility(ability);
    }

    private FaerieNoble(final FaerieNoble card) {
        super(card);
    }

    @Override
    public FaerieNoble copy() {
        return new FaerieNoble(this);
    }
}
