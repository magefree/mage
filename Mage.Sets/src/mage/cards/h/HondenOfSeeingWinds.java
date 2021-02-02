

package mage.cards.h;

import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class HondenOfSeeingWinds extends CardImpl {

    static final FilterControlledPermanent filter = new FilterControlledPermanent("Shrine");

    static {
        filter.add(SubType.SHRINE.getPredicate());
    }

    public HondenOfSeeingWinds(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{4}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHRINE);


        // At the beginning of your upkeep, draw a card for each Shrine you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter)), TargetController.YOU, false));
    }

    private HondenOfSeeingWinds(final HondenOfSeeingWinds card) {
        super(card);
    }

    @Override
    public HondenOfSeeingWinds copy() {
        return new HondenOfSeeingWinds(this);
    }

}
