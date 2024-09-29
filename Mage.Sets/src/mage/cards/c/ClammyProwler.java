package mage.cards.c;

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
public final class ClammyProwler extends CardImpl {

    private static final FilterPermanent filter = new FilterAttackingCreature("another target attacking creature");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ClammyProwler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(5);

        // Whenever Clammy Prowler attacks, another target attacking creature can't be blocked this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBeBlockedTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ClammyProwler(final ClammyProwler card) {
        super(card);
    }

    @Override
    public ClammyProwler copy() {
        return new ClammyProwler(this);
    }
}
