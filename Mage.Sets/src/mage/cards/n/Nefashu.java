
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author jeffwadsworth
 */
public final class Nefashu extends CardImpl {
    
    static final String rule = "Whenever Nefashu attacks, up to five target creatures each get -1/-1 until end of turn.";

    public Nefashu(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Whenever Nefashu attacks, up to five target creatures each get -1/-1 until end of turn.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(-1, -1, Duration.EndOfTurn), false, rule);
        ability.addTarget(new TargetCreaturePermanent(0, 5));
        this.addAbility(ability);
    }

    private Nefashu(final Nefashu card) {
        super(card);
    }

    @Override
    public Nefashu copy() {
        return new Nefashu(this);
    }
}