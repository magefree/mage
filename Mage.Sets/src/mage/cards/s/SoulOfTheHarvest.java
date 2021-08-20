
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;


/**
 * @author noxx
 */
public final class SoulOfTheHarvest extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("another nontoken creature");
    static {
        filter.add(AnotherPredicate.instance);
        filter.add(TokenPredicate.FALSE);
    }

    public SoulOfTheHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.addAbility(TrampleAbility.getInstance());

        // Whenever another nontoken creature enters the battlefield under your control, you may draw a card.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(1), filter, true));
    }

    private SoulOfTheHarvest(final SoulOfTheHarvest card) {
        super(card);
    }

    @Override
    public SoulOfTheHarvest copy() {
        return new SoulOfTheHarvest(this);
    }
}
