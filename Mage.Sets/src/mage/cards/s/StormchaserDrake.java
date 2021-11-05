package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;

/**
 *
 * @author weirddan455
 */
public final class StormchaserDrake extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public StormchaserDrake(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Stormchaser Drake becomes the target of a spell you control, draw a card.
        this.addAbility(new BecomesTargetTriggeredAbility(new DrawCardSourceControllerEffect(1), filter));
    }

    private StormchaserDrake(final StormchaserDrake card) {
        super(card);
    }

    @Override
    public StormchaserDrake copy() {
        return new StormchaserDrake(this);
    }
}
