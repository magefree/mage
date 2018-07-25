package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Viking extends CardImpl {

    public Viking(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{W}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Morph {W}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{W}")));

        // {W}, Turn Viking face down: Viking gains flying until end of turn.
    }

    public Viking(final Viking card) {
        super(card);
    }

    @Override
    public Viking copy() {
        return new Viking(this);
    }
}
