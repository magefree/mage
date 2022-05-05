
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public final class AshesOfTheFallen extends CardImpl {

    public AshesOfTheFallen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // As Ashes of the Fallen enters the battlefield, choose a creature type.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit)));

        // Each creature card in your graveyard has the chosen creature type in addition to its other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AshesOfTheFallenEffect()));
    }

    private AshesOfTheFallen(final AshesOfTheFallen card) {
        super(card);
    }

    @Override
    public AshesOfTheFallen copy() {
        return new AshesOfTheFallen(this);
    }
}

class AshesOfTheFallenEffect extends ContinuousEffectImpl {

    AshesOfTheFallenEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = "Each creature card in your graveyard has the chosen creature type in addition to its other types";
    }

    AshesOfTheFallenEffect(final AshesOfTheFallenEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (controller != null && permanent != null) {
            SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(permanent.getId(), game);
            if (subType != null) {
                for (UUID cardId : controller.getGraveyard()) {
                    Card card = game.getCard(cardId);
                    if (card != null && card.isCreature(game) && !card.hasSubtype(subType, game)) {
                        game.getState().getCreateMageObjectAttribute(card, game).getSubtype().add(subType);
                    }
                }
            } else {
                discard();
            }
            return true;
        }
        return false;
    }

    @Override
    public AshesOfTheFallenEffect copy() {
        return new AshesOfTheFallenEffect(this);
    }
}
