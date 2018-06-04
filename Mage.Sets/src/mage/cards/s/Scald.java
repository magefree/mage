
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.TapForManaAllTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public final class Scald extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("a player taps an Island");

    static {
        filter.add(new SubtypePredicate(SubType.ISLAND));
    }

    public Scald(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{R}");


        // Whenever a player taps an Island for mana, Scald deals 1 damage to that player.
        this.addAbility(new TapForManaAllTriggeredAbility(
                new DamageTargetEffect(1, true, "that player"),
                filter,
                SetTargetPointer.PLAYER));
    }

    public Scald(final Scald card) {
        super(card);
    }

    @Override
    public Scald copy() {
        return new Scald(this);
    }
}
