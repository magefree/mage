
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author Plopman
 */
public final class Groundbreaker extends CardImpl {

    public Groundbreaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{G}{G}{G}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // At the beginning of the end step, sacrifice Groundbreaker.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new SacrificeSourceEffect(), TargetController.NEXT, false));
    }

    private Groundbreaker(final Groundbreaker card) {
        super(card);
    }

    @Override
    public Groundbreaker copy() {
        return new Groundbreaker(this);
    }
}
