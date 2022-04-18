package mage.cards.j;

import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.OgreWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JoinTheMaestros extends CardImpl {

    public JoinTheMaestros(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{B}");

        // Casualty 2
        this.addAbility(new CasualtyAbility(this, 2));

        // Create a 4/3 black Ogre Warrior creature token.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new OgreWarriorToken()));
    }

    private JoinTheMaestros(final JoinTheMaestros card) {
        super(card);
    }

    @Override
    public JoinTheMaestros copy() {
        return new JoinTheMaestros(this);
    }
}
