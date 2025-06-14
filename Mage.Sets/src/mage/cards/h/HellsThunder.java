package mage.cards.h;

import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.UnearthAbility;
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
public final class HellsThunder extends CardImpl {

    public HellsThunder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{R}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false
        ));
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{4}{R}")));
    }

    private HellsThunder(final HellsThunder card) {
        super(card);
    }

    @Override
    public HellsThunder copy() {
        return new HellsThunder(this);
    }

}
