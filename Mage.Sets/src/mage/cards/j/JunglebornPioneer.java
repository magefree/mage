
package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.permanent.token.MerfolkHexproofToken;

/**
 *
 * @author JayDi85
 */
public final class JunglebornPioneer extends CardImpl {

    public JunglebornPioneer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Jungleborn Pioneer enters the battlefield, create a 1/1 blue Merfolk creature token with hexproof. (It can't be the target of spells or abilities your opponents control.)
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new MerfolkHexproofToken()),false);
        this.addAbility(ability);
    }

    private JunglebornPioneer(final JunglebornPioneer card) {
        super(card);
    }

    @Override
    public JunglebornPioneer copy() {
        return new JunglebornPioneer(this);
    }
}