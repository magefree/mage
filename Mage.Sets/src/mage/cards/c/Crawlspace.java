
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.ChangeMaxNumberThatCanAttackSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Crawlspace extends CardImpl {

    public Crawlspace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");

        // No more than two creatures can attack you each combat.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChangeMaxNumberThatCanAttackSourceEffect(2)));

    }

    private Crawlspace(final Crawlspace card) {
        super(card);
    }

    @Override
    public Crawlspace copy() {
        return new Crawlspace(this);
    }
}
