package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EumidianTerrabotanist extends CardImpl {

    public EumidianTerrabotanist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.INSECT);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Landfall -- Whenever a land you control enters, you gain 1 life.
        this.addAbility(new LandfallAbility(new GainLifeEffect(1)));
    }

    private EumidianTerrabotanist(final EumidianTerrabotanist card) {
        super(card);
    }

    @Override
    public EumidianTerrabotanist copy() {
        return new EumidianTerrabotanist(this);
    }
}
