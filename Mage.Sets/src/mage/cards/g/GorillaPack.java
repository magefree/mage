
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.ControlsPermanentsControllerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.combat.CantAttackUnlessDefenderControllsPermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author fireshoes
 */
public final class GorillaPack extends CardImpl {

    public GorillaPack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.APE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Gorilla Pack can't attack unless defending player controls a Forest.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantAttackUnlessDefenderControllsPermanent(new FilterLandPermanent(SubType.FOREST, "a Forest"))));

        // When you control no Forests, sacrifice Gorilla Pack.
        this.addAbility(new ControlsPermanentsControllerTriggeredAbility(
                new FilterLandPermanent(SubType.FOREST, "no Forests"), ComparisonType.EQUAL_TO, 0,
                new SacrificeSourceEffect()));
    }

    private GorillaPack(final GorillaPack card) {
        super(card);
    }

    @Override
    public GorillaPack copy() {
        return new GorillaPack(this);
    }
}
