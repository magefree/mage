
package mage.cards.m;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.command.CommandObject;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.ManaPoolItem;
import mage.players.Player;

/**
 * @author duncant
 */
public final class MycosynthLattice extends CardImpl {

    public MycosynthLattice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // All permanents are artifacts in addition to their other types.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PermanentsAreArtifactsEffect()));
        // All cards that aren't on the battlefield, spells, and permanents are colorless.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EverythingIsColorlessEffect()));
        // Players may spend mana as though it were mana of any color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ManaCanBeSpentAsAnyColorEffect()));
    }

    public MycosynthLattice(final MycosynthLattice card) {
        super(card);
    }

    @Override
    public MycosynthLattice copy() {
        return new MycosynthLattice(this);
    }
}

class PermanentsAreArtifactsEffect extends ContinuousEffectImpl {

    public PermanentsAreArtifactsEffect() {
        super(Duration.WhileOnBattlefield, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Neutral);
        staticText = "All permanents are artifacts in addition to their other types";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
            perm.addCardType(CardType.ARTIFACT);
        }
        return true;
    }

    @Override
    public PermanentsAreArtifactsEffect copy() {
        return new PermanentsAreArtifactsEffect(this);
    }

    private PermanentsAreArtifactsEffect(PermanentsAreArtifactsEffect effect) {
        super(effect);
    }
}

class EverythingIsColorlessEffect extends ContinuousEffectImpl {

    public EverythingIsColorlessEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Neutral);
        staticText = "All cards that aren't on the battlefield, spells, and permanents are colorless";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            ObjectColor colorless = new ObjectColor();
            // permaments
            for (Permanent perm : game.getBattlefield().getActivePermanents(source.getControllerId(), game)) {
                perm.getColor(game).setColor(colorless);
            }
            // spells
            for (MageObject object : game.getStack()) {
                if (object instanceof Spell) {
                    object.getColor(game).setColor(colorless);
                }
            }
            // exile
            for (Card card : game.getExile().getAllCards(game)) {
                game.getState().getCreateCardAttribute(card, game).getColor().setColor(colorless);
            }
            // command
            for (CommandObject commandObject : game.getState().getCommand()) {
                commandObject.getColor(game).setColor(colorless);
            }
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    // hand
                    for (Card card : player.getHand().getCards(game)) {
                        game.getState().getCreateCardAttribute(card, game).getColor().setColor(colorless);
                    }
                    // library
                    for (Card card : player.getLibrary().getCards(game)) {
                        game.getState().getCreateCardAttribute(card, game).getColor().setColor(colorless);
                    }
                    // graveyard
                    for (Card card : player.getGraveyard().getCards(game)) {
                        game.getState().getCreateCardAttribute(card, game).getColor().setColor(colorless);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public EverythingIsColorlessEffect copy() {
        return new EverythingIsColorlessEffect(this);
    }

    private EverythingIsColorlessEffect(EverythingIsColorlessEffect effect) {
        super(effect);
    }
}

class ManaCanBeSpentAsAnyColorEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public ManaCanBeSpentAsAnyColorEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Players may spend mana as though it were mana of any color";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && game.getState().getPlayersInRange(controller.getId(), game).contains(affectedControllerId);
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }

    @Override
    public ManaCanBeSpentAsAnyColorEffect copy() {
        return new ManaCanBeSpentAsAnyColorEffect(this);
    }

    private ManaCanBeSpentAsAnyColorEffect(ManaCanBeSpentAsAnyColorEffect effect) {
        super(effect);
    }
}
