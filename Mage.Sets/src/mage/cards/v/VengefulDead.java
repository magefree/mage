
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesThisOrAnotherCreatureTriggeredAbility;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class VengefulDead extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Zombie");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public VengefulDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever Vengeful Dead or another Zombie dies, each opponent loses 1 life.
        this.addAbility(new DiesThisOrAnotherCreatureTriggeredAbility(new LoseLifeOpponentsEffect(1), false, filter));
    }

    private VengefulDead(final VengefulDead card) {
        super(card);
    }

    @Override
    public VengefulDead copy() {
        return new VengefulDead(this);
    }
}
