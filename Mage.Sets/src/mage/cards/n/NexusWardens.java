package mage.cards.n;

import mage.MageInt;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NexusWardens extends CardImpl {

    public NexusWardens(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.SATYR);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Constellation — Whenever an enchantment enters the battlefield under you control, you gain 2 life.
        this.addAbility(new ConstellationAbility(new GainLifeEffect(2), false, false));
    }

    private NexusWardens(final NexusWardens card) {
        super(card);
    }

    @Override
    public NexusWardens copy() {
        return new NexusWardens(this);
    }
}
