package mage.cards.b;

import mage.MageInt;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class BallLightning extends CardImpl {

    public BallLightning(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(6);
        this.toughness = new MageInt(1);

        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false
        ));
    }

    private BallLightning(final BallLightning card) {
        super(card);
    }

    @Override
    public BallLightning copy() {
        return new BallLightning(this);
    }

}
