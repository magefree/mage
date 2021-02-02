
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SilumgarButcher extends CardImpl {

    public SilumgarButcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.DJINN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Exploit (When this creature enters the battlefield, you may sacrifice a creature.)
        this.addAbility(new ExploitAbility());
        
        // When Silumgar Butcher exploits a creature, target creature gets -3/-3 until end of turn.
        Ability ability = new ExploitCreatureTriggeredAbility(new BoostTargetEffect(-3,-3, Duration.EndOfTurn), false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
        
    }

    private SilumgarButcher(final SilumgarButcher card) {
        super(card);
    }

    @Override
    public SilumgarButcher copy() {
        return new SilumgarButcher(this);
    }
}
