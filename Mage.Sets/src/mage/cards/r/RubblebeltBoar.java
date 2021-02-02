package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class RubblebeltBoar extends CardImpl {

    public RubblebeltBoar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.BOAR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Rubblebelt Boar enters the battlefield, target creature gets +2/+0 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new BoostTargetEffect(2, 0, Duration.EndOfTurn)
        );
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private RubblebeltBoar(final RubblebeltBoar card) {
        super(card);
    }

    @Override
    public RubblebeltBoar copy() {
        return new RubblebeltBoar(this);
    }
}
