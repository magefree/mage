package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class ScreechingBat extends CardImpl {

    public ScreechingBat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.subtype.add(SubType.BAT);

        this.secondSideCardClazz = StalkingVampire.class;

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, you may pay {2}{B}{B}. If you do, transform Screeching Bat.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DoIfCostPaid(
                new TransformSourceEffect(),
                new ManaCostsImpl<>("{2}{B}{B}")
        ), TargetController.YOU, false));
    }

    private ScreechingBat(final ScreechingBat card) {
        super(card);
    }

    @Override
    public ScreechingBat copy() {
        return new ScreechingBat(this);
    }
}
