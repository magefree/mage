package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OggyarBattleSeer extends CardImpl {

    public OggyarBattleSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{R}");

        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // {T}: Scry 1.
        this.addAbility(new SimpleActivatedAbility(new ScryEffect(1, false), new TapSourceCost()));
    }

    private OggyarBattleSeer(final OggyarBattleSeer card) {
        super(card);
    }

    @Override
    public OggyarBattleSeer copy() {
        return new OggyarBattleSeer(this);
    }
}
