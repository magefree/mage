
package mage.cards.c;

import java.util.UUID;
import mage.Mana;
import mage.ObjectColor;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ColorPredicate;

/**
 *
 * @author North
 */
public final class CryptOfAgadeem extends CardImpl {

    private static final FilterCreatureCard filter = new FilterCreatureCard("black creature card");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public CryptOfAgadeem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Crypt of Agadeem enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
        // {2}, {T}: Add {B} for each black creature card in your graveyard.
        DynamicManaAbility ability = new DynamicManaAbility(Mana.BlackMana(1), new CardsInControllerGraveyardCount(filter), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private CryptOfAgadeem(final CryptOfAgadeem card) {
        super(card);
    }

    @Override
    public CryptOfAgadeem copy() {
        return new CryptOfAgadeem(this);
    }
}
