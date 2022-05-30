


package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LevelX
 */
public final class CrystalSeer extends CardImpl {

    public CrystalSeer (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Crystal Seer enters the battlefield, look at the top four cards of your library, then put them back in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(4)));

        // {4}{U}: Return Crystal Seer to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), new ManaCostsImpl<>("{4}{U}")));
    }

    public CrystalSeer (final CrystalSeer card) {
        super(card);
    }

    @Override
    public CrystalSeer copy() {
        return new CrystalSeer(this);
    }

}

