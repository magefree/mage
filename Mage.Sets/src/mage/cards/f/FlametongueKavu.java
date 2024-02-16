
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class FlametongueKavu extends CardImpl {

    public FlametongueKavu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.KAVU);

        this.power = new MageInt(4);
        this.toughness = new MageInt(2);
        Ability ability = new EntersBattlefieldTriggeredAbility(new DamageTargetEffect(4, "it"), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private FlametongueKavu(final FlametongueKavu card) {
        super(card);
    }

    @Override
    public FlametongueKavu copy() {
        return new FlametongueKavu(this);
    }
}
