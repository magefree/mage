package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BrackwaterElemental extends CardImpl {

    public BrackwaterElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // When Brackwater Elemental attacks or blocks, sacrifice it at the beginning of the next end step.
        this.addAbility(new AttacksOrBlocksTriggeredAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new SacrificeSourceEffect())
        ).setText("sacrifice it at the beginning of the next end step"), false).setTriggerPhrase("When {this} attacks or blocks, "));

        // Unearth {2}{U}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{2}{U}")));
    }

    private BrackwaterElemental(final BrackwaterElemental card) {
        super(card);
    }

    @Override
    public BrackwaterElemental copy() {
        return new BrackwaterElemental(this);
    }
}
