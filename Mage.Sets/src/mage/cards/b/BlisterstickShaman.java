

package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author Loki
 */
public final class BlisterstickShaman extends CardImpl {

    public BlisterstickShaman (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN, SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(1, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    public BlisterstickShaman (final BlisterstickShaman card) {
        super(card);
    }

    @Override
    public BlisterstickShaman copy() {
        return new BlisterstickShaman(this);
    }

}
