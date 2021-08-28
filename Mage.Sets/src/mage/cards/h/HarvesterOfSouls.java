
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;

/**
 * @author noxx
 */
public final class HarvesterOfSouls extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("another nontoken creature");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
    }

    public HarvesterOfSouls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(DeathtouchAbility.getInstance());

        // Whenever another nontoken creature dies, you may draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(new DrawCardSourceControllerEffect(1), true, filter));
    }

    private HarvesterOfSouls(final HarvesterOfSouls card) {
        super(card);
    }

    @Override
    public HarvesterOfSouls copy() {
        return new HarvesterOfSouls(this);
    }
}
