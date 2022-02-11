package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author fireshoes
 */
public final class CorruptOfficial extends CardImpl {

    public CorruptOfficial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {2}{B}: Regenerate Corrupt Official.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{2}{B}")));

        // Whenever Corrupt Official becomes blocked, defending player discards a card at random.
        this.addAbility(new BecomesBlockedSourceTriggeredAbility(
                new DiscardTargetEffect(1, true).setText("defending player discards a card at random"),
                false, true));
    }

    private CorruptOfficial(final CorruptOfficial card) {
        super(card);
    }

    @Override
    public CorruptOfficial copy() {
        return new CorruptOfficial(this);
    }
}
