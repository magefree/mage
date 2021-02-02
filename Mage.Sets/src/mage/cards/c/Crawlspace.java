
package mage.cards.c;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ChangeMaxAttackedBySourceEffect(2)));

    }

    private Crawlspace(final Crawlspace card) {
        super(card);
    }

    @Override
    public Crawlspace copy() {
        return new Crawlspace(this);
    }
}

class ChangeMaxAttackedBySourceEffect extends ContinuousEffectImpl {

    private final int maxAttackedBy;

    public ChangeMaxAttackedBySourceEffect(int maxAttackedBy) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        this.maxAttackedBy = maxAttackedBy;
        staticText = "No more than two creatures can attack you each combat";
    }

    public ChangeMaxAttackedBySourceEffect(final ChangeMaxAttackedBySourceEffect effect) {
        super(effect);
        this.maxAttackedBy = effect.maxAttackedBy;
    }

    @Override
    public ChangeMaxAttackedBySourceEffect copy() {
        return new ChangeMaxAttackedBySourceEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                Player controller = game.getPlayer(source.getControllerId());
                if (controller != null) {
                    // Change the rule
                    if (controller.getMaxAttackedBy()> maxAttackedBy) {
                        controller.setMaxAttackedBy(maxAttackedBy);
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
