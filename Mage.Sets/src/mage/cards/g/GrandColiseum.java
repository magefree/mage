
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.effects.common.DamageControllerEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author KholdFuzion
 */
public final class GrandColiseum extends CardImpl {

    public GrandColiseum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Grand Coliseum enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {tap}: Add one mana of any color. Grand Coliseum deals 1 damage to you.
        Ability ability = new AnyColorManaAbility();
        ability.addEffect(new DamageControllerEffect(1));
        this.addAbility(ability);
    }

    private GrandColiseum(final GrandColiseum card) {
        super(card);
    }

    @Override
    public GrandColiseum copy() {
        return new GrandColiseum(this);
    }
}
