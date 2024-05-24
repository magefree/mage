package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.combat.GoadTargetEffect;
import mage.abilities.keyword.BattalionAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class SontaranGeneral extends CardImpl {

    public SontaranGeneral(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Battalion -- Whenever Sontaran General and at least two other creatures attack, for each opponent,
        // goad up to one target creature that player controls. Those creatures can't block this turn.
        Ability ability = new BattalionAbility(new GoadTargetEffect()
                .setText("for each opponent, goad up to one target creature that player controls."));
        ability.addEffect(new CantBlockTargetEffect(Duration.EndOfTurn)
                .setText("Those creatures can't block this turn."));
        ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private SontaranGeneral(final SontaranGeneral card) {
        super(card);
    }

    @Override
    public SontaranGeneral copy() {
        return new SontaranGeneral(this);
    }
}
