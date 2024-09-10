

package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Loki
 */
public final class NeurokInvisimancer extends CardImpl {

    public NeurokInvisimancer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Neurok Invisimancer can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());
        // When Neurok Invisimancer enters the battlefield, target creature can't be blocked this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CantBeBlockedTargetEffect());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NeurokInvisimancer(final NeurokInvisimancer card) {
        super(card);
    }

    @Override
    public NeurokInvisimancer copy() {
        return new NeurokInvisimancer(this);
    }

}
