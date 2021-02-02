
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class MishrasFactory extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Assembly-Worker creature");

    static {
        filter.add(SubType.ASSEMBLY_WORKER.getPredicate());
    }

    public MishrasFactory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        this.addAbility(new ColorlessManaAbility());
        // {1}: Mishra's Factory becomes a 2/2 Assembly-Worker artifact creature until end of turn. It's still a land.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BecomesCreatureSourceEffect(new AssemblyWorkerToken(), "land", Duration.EndOfTurn),
                new GenericManaCost(1)));
        // {tap}: Target Assembly-Worker creature gets +1/+1 until end of turn.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostTargetEffect(1, 1, Duration.EndOfTurn),
                new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent(filter));
        this.addAbility(ability);
    }

    private MishrasFactory(final MishrasFactory card) {
        super(card);
    }

    @Override
    public MishrasFactory copy() {
        return new MishrasFactory(this);
    }
}

class AssemblyWorkerToken extends TokenImpl {

    public AssemblyWorkerToken() {
        super("Assembly-Worker", "2/2 Assembly-Worker artifact creature");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ASSEMBLY_WORKER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }
    public AssemblyWorkerToken(final AssemblyWorkerToken token) {
        super(token);
    }

    public AssemblyWorkerToken copy() {
        return new AssemblyWorkerToken(this);
    }
}
