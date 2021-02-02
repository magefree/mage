
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.LookAtTargetPlayerHandEffect;
import mage.abilities.keyword.RepairAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Styxo
 */
public final class ProbeDroid extends CardImpl {

    public ProbeDroid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.DROID);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Probe Droid enters the battlefield, target player reveals their hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LookAtTargetPlayerHandEffect());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Repair 3
        this.addAbility(new RepairAbility(3));
    }

    private ProbeDroid(final ProbeDroid card) {
        super(card);
    }

    @Override
    public ProbeDroid copy() {
        return new ProbeDroid(this);
    }
}
