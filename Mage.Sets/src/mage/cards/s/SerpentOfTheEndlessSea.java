
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author North
 */
public final class SerpentOfTheEndlessSea extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Islands you control");

    static {
        filter.add(SubType.ISLAND.getPredicate());
    }

    public SerpentOfTheEndlessSea(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SERPENT);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Serpent of the Endless Sea's power and toughness are each equal to the number of Islands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerToughnessSourceEffect(new PermanentsOnBattlefieldCount(filter), Duration.EndOfGame)));
        // Serpent of the Endless Sea can't attack unless defending player controls an Island.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.ISLAND,"an Island"))));
    }

    private SerpentOfTheEndlessSea(final SerpentOfTheEndlessSea card) {
        super(card);
    }

    @Override
    public SerpentOfTheEndlessSea copy() {
        return new SerpentOfTheEndlessSea(this);
    }
}
