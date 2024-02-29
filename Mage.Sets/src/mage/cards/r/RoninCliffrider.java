package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageAllControlledTargetEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class RoninCliffrider extends CardImpl {

    public RoninCliffrider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));
        // Whenever Ronin Cliffrider attacks, you may have it deal 1 damage to each creature defending player controls.
        this.addAbility(new AttacksTriggeredAbility(new DamageAllControlledTargetEffect(1)
                .setText("you may have it deal 1 damage to each creature defending player controls"),
                true, null, SetTargetPointer.PLAYER));
    }

    private RoninCliffrider(final RoninCliffrider card) {
        super(card);
    }

    @Override
    public RoninCliffrider copy() {
        return new RoninCliffrider(this);
    }
}
