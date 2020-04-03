package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.MutatesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardEachPlayerEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.MutateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CavernWhisperer extends CardImpl {

    public CavernWhisperer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Mutate {3}{B}
        this.addAbility(new MutateAbility(this, "{3}{B}"));

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever this creature mutates, each opponent discards a card.
        this.addAbility(new MutatesSourceTriggeredAbility(new DiscardEachPlayerEffect(TargetController.OPPONENT)));
    }

    private CavernWhisperer(final CavernWhisperer card) {
        super(card);
    }

    @Override
    public CavernWhisperer copy() {
        return new CavernWhisperer(this);
    }
}
