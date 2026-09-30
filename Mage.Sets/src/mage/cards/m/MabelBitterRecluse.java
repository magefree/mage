package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.RemoveUpToAmountCountersEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MabelBitterRecluse extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("another target creature or planeswalker");

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(Predicates.or(
            CardType.CREATURE.getPredicate(),
            CardType.PLANESWALKER.getPredicate()
        ));
    }

    public MabelBitterRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOUSE);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Mabel enters, remove up to three counters from another target creature or planeswalker.
        Ability ability = new EntersBattlefieldTriggeredAbility(new RemoveUpToAmountCountersEffect(3));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private MabelBitterRecluse(final MabelBitterRecluse card) {
        super(card);
    }

    @Override
    public MabelBitterRecluse copy() {
        return new MabelBitterRecluse(this);
    }
}
