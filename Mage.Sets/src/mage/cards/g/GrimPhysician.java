package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GrimPhysician extends CardImpl {

    public GrimPhysician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Grim Physician dies, target creature an opponent controls gets -1/-1 until end of turn.
        Ability ability = new DiesSourceTriggeredAbility(new BoostTargetEffect(-1, -1));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private GrimPhysician(final GrimPhysician card) {
        super(card);
    }

    @Override
    public GrimPhysician copy() {
        return new GrimPhysician(this);
    }
}
