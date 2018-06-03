
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox
 */
public final class AurochsHerd extends CardImpl {

    private static final FilterCard filter1 = new FilterCard("Aurochs card");
    private static final FilterAttackingCreature filter2 = new FilterAttackingCreature("other attacking Aurochs");

    static {
        filter1.add(new SubtypePredicate(SubType.AUROCHS));
        filter2.add(new SubtypePredicate(SubType.AUROCHS));
        filter2.add(new AnotherPredicate());
    }

    public AurochsHerd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{G}");
        this.subtype.add(SubType.AUROCHS);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // When Aurochs Herd enters the battlefield, you may search your library for an Aurochs card, reveal it, and put it into your hand. If you do, shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SearchLibraryPutInHandEffect(
            new TargetCardInLibrary(filter1), true), true));
        // Whenever Aurochs Herd attacks, it gets +1/+0 until end of turn for each other attacking Aurochs.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter2, 1);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(value, new StaticValue(0), Duration.EndOfTurn, true), false));
    }

    public AurochsHerd(final AurochsHerd card) {
        super(card);
    }

    @Override
    public AurochsHerd copy() {
        return new AurochsHerd(this);
    }
}
