
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 *
 * @author LoneFox
 */
public final class AvenFateshaper extends CardImpl {

    public AvenFateshaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{6}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Aven Fateshaper enters the battlefield, look at the top four cards of your library, then put them back in any order.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new LookLibraryControllerEffect(4)));
        // {4}{U}: Look at the top four cards of your library, then put them back in any order.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new LookLibraryControllerEffect(4), new ManaCostsImpl<>("{4}{U}")));
    }

    private AvenFateshaper(final AvenFateshaper card) {
        super(card);
    }

    @Override
    public AvenFateshaper copy() {
        return new AvenFateshaper(this);
    }
}
