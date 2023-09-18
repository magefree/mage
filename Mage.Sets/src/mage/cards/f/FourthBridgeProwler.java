package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class FourthBridgeProwler extends CardImpl {

    public FourthBridgeProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Fourth Bridge Prowler enters the battlefield, you may have target creature get -1/-1 until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BoostTargetEffect(-1, -1)
                .setText("you may have target creature get -1/-1 until end of turn"), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FourthBridgeProwler(final FourthBridgeProwler card) {
        super(card);
    }

    @Override
    public FourthBridgeProwler copy() {
        return new FourthBridgeProwler(this);
    }
}
