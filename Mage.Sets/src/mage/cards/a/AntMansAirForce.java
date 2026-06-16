package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AntMansAirForce extends CardImpl {

    public AntMansAirForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever this creature attacks, up to one target creature gets -1/-0 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-1, 0));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private AntMansAirForce(final AntMansAirForce card) {
        super(card);
    }

    @Override
    public AntMansAirForce copy() {
        return new AntMansAirForce(this);
    }
}
