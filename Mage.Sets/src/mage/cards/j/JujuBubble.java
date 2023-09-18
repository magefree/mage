package mage.cards.j;

import java.util.UUID;
import mage.abilities.common.PlayCardTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;

/**
 *
 * @author TheElk801
 */
public final class JujuBubble extends CardImpl {

    public JujuBubble(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // Cumulative upkeep {1}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{1}")));

        // When you play a card, sacrifice Juju Bubble.
        this.addAbility(new PlayCardTriggeredAbility(TargetController.YOU, Zone.BATTLEFIELD,
                new SacrificeSourceEffect(), false));

        // {2}: You gain 1 life.
        this.addAbility(new SimpleActivatedAbility(new GainLifeEffect(1), new GenericManaCost(1)));
    }

    private JujuBubble(final JujuBubble card) {
        super(card);
    }

    @Override
    public JujuBubble copy() {
        return new JujuBubble(this);
    }
}