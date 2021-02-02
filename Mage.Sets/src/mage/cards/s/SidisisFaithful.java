
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SidisisFaithful extends CardImpl {

    public SidisisFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Exploit
        this.addAbility(new ExploitAbility());
        
        // When Sidisi's Faithful exploits a creature, return target creature to its owner's hand.
        Ability ability = new ExploitCreatureTriggeredAbility(new ReturnToHandTargetEffect(), false);
        ability.addTarget(new TargetCreaturePermanent());        
        this.addAbility(ability);
    }

    private SidisisFaithful(final SidisisFaithful card) {
        super(card);
    }

    @Override
    public SidisisFaithful copy() {
        return new SidisisFaithful(this);
    }
}
