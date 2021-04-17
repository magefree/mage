
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class RagingSwordtooth extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("other creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public RagingSwordtooth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Raging Swordtooth enters the battlefield, it deals 1 damage to each other creature.
        addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, "it", filter)));
    }

    private RagingSwordtooth(final RagingSwordtooth card) {
        super(card);
    }

    @Override
    public RagingSwordtooth copy() {
        return new RagingSwordtooth(this);
    }
}
