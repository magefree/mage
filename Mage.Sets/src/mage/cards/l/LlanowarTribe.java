package mage.cards.l;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LlanowarTribe extends CardImpl {

    public LlanowarTribe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}: Add {G}{G}{G}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.GreenMana(3), new TapSourceCost()));
    }

    private LlanowarTribe(final LlanowarTribe card) {
        super(card);
    }

    @Override
    public LlanowarTribe copy() {
        return new LlanowarTribe(this);
    }
}
