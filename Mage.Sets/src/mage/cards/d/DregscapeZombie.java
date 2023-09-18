
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DregscapeZombie extends CardImpl {

    public DregscapeZombie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        // Unearth {B} ({B}: Return this card from your graveyard to the battlefield. It gains haste. Exile it at the beginning of the next end step or if it would leave the battlefield. Unearth only as a sorcery.)
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{B}")));
    }

    private DregscapeZombie(final DregscapeZombie card) {
        super(card);
    }

    @Override
    public DregscapeZombie copy() {
        return new DregscapeZombie(this);
    }

}
