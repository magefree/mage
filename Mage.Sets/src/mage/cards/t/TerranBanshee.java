package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class TerranBanshee extends CardImpl {

    public TerranBanshee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}{R}");
        
        this.subtype.add(SubType.TERRAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all damage Terran Banshee would deal to creatures with flying.
        // Morph {2}{R}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{R}")));

    }

    public TerranBanshee(final TerranBanshee card) {
        super(card);
    }

    @Override
    public TerranBanshee copy() {
        return new TerranBanshee(this);
    }
}
