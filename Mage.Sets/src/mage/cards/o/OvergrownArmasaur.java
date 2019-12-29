
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SaprolingToken;

/**
 *
 * @author JayDi85
 */
public final class OvergrownArmasaur extends CardImpl {

    public OvergrownArmasaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Enrage - Whenever Overgrown Armasaur is dealt damage, create a 1/1 green Saproling creature token.
        Ability ability = new DealtDamageToSourceTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken()),
                false,
                true);
        this.addAbility(ability);
    }

    public OvergrownArmasaur(final OvergrownArmasaur card) {
        super(card);
    }

    @Override
    public OvergrownArmasaur copy() {
        return new OvergrownArmasaur(this);
    }
}