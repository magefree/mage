
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author fireshoes
 */
public final class MephiticOoze extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("artifact you control");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    public MephiticOoze(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.OOZE);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Mephitic Ooze gets +1/+0 for each artifact you control.
        Effect effect = new BoostSourceEffect(new PermanentsOnBattlefieldCount(filter), new StaticValue(0), Duration.WhileOnBattlefield);
        effect.setText("{this} gets +1/+0 for each artifact you control");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));

        // Whenever Mephitic Ooze deals combat damage to a creature, destroy that creature. The creature can't be regenerated.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(new DestroyTargetEffect(true), false, true));
    }

    public MephiticOoze(final MephiticOoze card) {
        super(card);
    }

    @Override
    public MephiticOoze copy() {
        return new MephiticOoze(this);
    }
}
