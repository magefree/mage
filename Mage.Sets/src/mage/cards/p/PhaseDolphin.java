package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhaseDolphin extends CardImpl {

    private static final FilterPermanent filter
            = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public PhaseDolphin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Phase Dolphin attacks, another target attacking creature can't be blocked this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBeBlockedTargetEffect()
                .setText("another target attacking creature can't be blocked this turn"), false);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private PhaseDolphin(final PhaseDolphin card) {
        super(card);
    }

    @Override
    public PhaseDolphin copy() {
        return new PhaseDolphin(this);
    }
}
