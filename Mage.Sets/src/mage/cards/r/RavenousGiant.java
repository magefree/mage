package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RavenousGiant extends CardImpl {

    public RavenousGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // At the beginning of your upkeep, Ravenous Giant deals 1 damage to you.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageControllerEffect(1),
                TargetController.YOU, false
        ));
    }

    private RavenousGiant(final RavenousGiant card) {
        super(card);
    }

    @Override
    public RavenousGiant copy() {
        return new RavenousGiant(this);
    }
}
