package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.MutatesSourceTriggeredAbility;
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
public final class ZagothMamba extends CardImpl {

    public ZagothMamba(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.SNAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever this creature mutates, target creature an opponent controls gets -2/-2 until end of turn.
        Ability ability = new MutatesSourceTriggeredAbility(new BoostTargetEffect(-2, -2));
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private ZagothMamba(final ZagothMamba card) {
        super(card);
    }

    @Override
    public ZagothMamba copy() {
        return new ZagothMamba(this);
    }
}
