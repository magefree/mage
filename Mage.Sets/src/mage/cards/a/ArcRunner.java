package mage.cards.a;

import mage.MageInt;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.HasteAbility;
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
public final class ArcRunner extends CardImpl {

    public ArcRunner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.OX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        this.addAbility(HasteAbility.getInstance());

        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false
        ));
    }

    private ArcRunner(final ArcRunner card) {
        super(card);
    }

    @Override
    public ArcRunner copy() {
        return new ArcRunner(this);
    }

}
