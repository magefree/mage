
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesMonstrousSourceTriggeredAbility;
import mage.abilities.effects.common.combat.MustBeBlockedByTargetSourceEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class NexuStalker extends CardImpl {

    public NexuStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Hexproof
        this.addAbility(HexproofAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {2}{G}: Monstrosity 1.
        this.addAbility(new MonstrosityAbility("{2}{G}", 1));

        //  When Nexu Stalker becomes monstrous, target creature blocks it this turn if able.
        Ability ability = new BecomesMonstrousSourceTriggeredAbility(new MustBeBlockedByTargetSourceEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NexuStalker(final NexuStalker card) {
        super(card);
    }

    @Override
    public NexuStalker copy() {
        return new NexuStalker(this);
    }
}
