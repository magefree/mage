package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.HorrorToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HuntedLammasu extends CardImpl {

    public HuntedLammasu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{W}");
        this.subtype.add(SubType.LAMMASU);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Hunted Lammasu enters the battlefield, create a 4/4 black Horror creature token under target opponent's control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new HorrorToken()), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private HuntedLammasu(final HuntedLammasu card) {
        super(card);
    }

    @Override
    public HuntedLammasu copy() {
        return new HuntedLammasu(this);
    }
}
