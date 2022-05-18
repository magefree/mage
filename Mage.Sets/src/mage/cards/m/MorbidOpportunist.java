package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MorbidOpportunist extends CardImpl {

    public MorbidOpportunist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever one or more other creatures die, draw a card. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, true
        ).setTriggerPhrase("Whenever one or more other creatures die, ").setTriggersOnce(true));
    }

    private MorbidOpportunist(final MorbidOpportunist card) {
        super(card);
    }

    @Override
    public MorbidOpportunist copy() {
        return new MorbidOpportunist(this);
    }
}
