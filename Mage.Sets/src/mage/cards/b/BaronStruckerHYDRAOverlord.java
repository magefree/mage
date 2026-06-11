package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.effects.keyword.ConniveTargetEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author muz
 */
public final class BaronStruckerHYDRAOverlord extends CardImpl {

    private static final FilterCard filter = new FilterCard("Villain spells");
    private static final FilterPermanent filter2 = new FilterPermanent(SubType.VILLAIN, "another Villain");


    static {
        filter.add(SubType.VILLAIN.getPredicate());
        filter2.add(AnotherPredicate.instance);
    }

    public BaronStruckerHYDRAOverlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Villain spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever another Villain you control enters, you may have it connive. Do this only once each turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
            Zone.BATTLEFIELD, new ConniveTargetEffect(), filter2, true, SetTargetPointer.PERMANENT
        ).setDoOnlyOnceEachTurn(true);
        this.addAbility(ability);
    }

    private BaronStruckerHYDRAOverlord(final BaronStruckerHYDRAOverlord card) {
        super(card);
    }

    @Override
    public BaronStruckerHYDRAOverlord copy() {
        return new BaronStruckerHYDRAOverlord(this);
    }
}
