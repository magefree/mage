package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author jeffwadsworth
 */
public final class BazaarKrovod extends CardImpl {

    static final FilterAttackingCreature filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public BazaarKrovod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Bazaar Krovod attacks, another target attacking creature gets +0/+2 until end of turn. Untap that creature.
        Ability ability = new AttacksTriggeredAbility(new BoostTargetEffect(0, 2, Duration.EndOfTurn), false);
        ability.addEffect(new UntapTargetEffect().setText("Untap that creature"));
        ability.addTarget(new TargetAttackingCreature(1, 1, filter, false));
        this.addAbility(ability);
    }

    private BazaarKrovod(final BazaarKrovod card) {
        super(card);
    }

    @Override
    public BazaarKrovod copy() {
        return new BazaarKrovod(this);
    }
}
