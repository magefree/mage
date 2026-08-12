package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class AresGodOfWar extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("an attacking creature you control");

    static {
        filter.add(AttackingPredicate.instance);
    }

    public AresGodOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOD);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Ares attacks each combat if able.
        this.addAbility(new AttacksEachCombatStaticAbility());

        // Whenever an attacking creature you control dies, return that card to its owner's hand.
        this.addAbility(new DiesCreatureTriggeredAbility(
            new ReturnToHandTargetEffect()
                .setText("return that card to its owner's hand"),
            false, filter, true
        ));
    }

    private AresGodOfWar(final AresGodOfWar card) {
        super(card);
    }

    @Override
    public AresGodOfWar copy() {
        return new AresGodOfWar(this);
    }
}
