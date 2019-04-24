

package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MetalcraftCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author Loki
 */
public final class CarapaceForger extends CardImpl {
    private static final String text = "<i>Metalcraft</i> &mdash; Carapace Forger gets +2/+2 as long as you control three or more artifacts";

    public CarapaceForger (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.ARTIFICER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        ContinuousEffect boostSource = new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, MetalcraftCondition.instance, text);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
    }

    public CarapaceForger (final CarapaceForger card) {
        super(card);
    }

    @Override
    public CarapaceForger copy() {
        return new CarapaceForger(this);
    }

}
