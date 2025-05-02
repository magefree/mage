
package mage.cards.k;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class KozileksTranslator extends CardImpl {

    public KozileksTranslator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.DRONE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Devoid
        this.addAbility(new DevoidAbility(this.color));

        // Pay 1 life: Add {C}. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.ColorlessMana(1)), new PayLifeCost(1)
        ));
    }

    private KozileksTranslator(final KozileksTranslator card) {
        super(card);
    }

    @Override
    public KozileksTranslator copy() {
        return new KozileksTranslator(this);
    }
}
