package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreaturePermanent;

/**
 *
 * @author weirddan455
 */
public final class VampireSlayer extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent(SubType.VAMPIRE, "a Vampire");

    public VampireSlayer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Vampire Slayer deals damage to a Vampire, destroy that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(
                new DestroyTargetEffect(), false, false, true, filter
        ));
    }

    private VampireSlayer(final VampireSlayer card) {
        super(card);
    }

    @Override
    public VampireSlayer copy() {
        return new VampireSlayer(this);
    }
}
