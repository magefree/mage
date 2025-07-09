package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author North
 */
public final class TorchSlinger extends CardImpl {

    public TorchSlinger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Kicker {1}{R} (You may pay an additional {1}{R} as you cast this spell.)
        this.addAbility(new KickerAbility("{1}{R}"));

        // When Torch Slinger enters the battlefield, if it was kicked, it deals 2 damage to target creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(2, "it")).withInterveningIf(KickedCondition.ONCE);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private TorchSlinger(final TorchSlinger card) {
        super(card);
    }

    @Override
    public TorchSlinger copy() {
        return new TorchSlinger(this);
    }
}
