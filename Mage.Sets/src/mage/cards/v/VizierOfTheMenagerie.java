
package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.AsThoughManaEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.ManaPoolItem;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class VizierOfTheMenagerie extends CardImpl {

    public VizierOfTheMenagerie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.subtype.add(SubType.NAGA);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // You may look at the top card of your library. (You may do this at any time.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LookAtTopCardOfLibraryAnyTimeEffect()));

        // You may cast the top card of your library if it's a creature card.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VizierOfTheMenagerieTopCardCastEffect()));

        // You may spend mana as though it were mana of any type to cast creature spells.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VizierOfTheMenagerieManaEffect()));

    }

    public VizierOfTheMenagerie(final VizierOfTheMenagerie card) {
        super(card);
    }

    @Override
    public VizierOfTheMenagerie copy() {
        return new VizierOfTheMenagerie(this);
    }
}

class VizierOfTheMenagerieTopCardCastEffect extends AsThoughEffectImpl {

    public VizierOfTheMenagerieTopCardCastEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may cast the top card of your library if it's a creature card";
    }

    public VizierOfTheMenagerieTopCardCastEffect(final VizierOfTheMenagerieTopCardCastEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VizierOfTheMenagerieTopCardCastEffect copy() {
        return new VizierOfTheMenagerieTopCardCastEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId())) {
            Card card = game.getCard(objectId);
            if (card != null) {
                Player controller = game.getPlayer(affectedControllerId);
                if (controller != null) {
                    Card topCard = controller.getLibrary().getFromTop(game);
                    MageObject vizierOfTheMenagerie = game.getObject(source.getSourceId());
                    if (vizierOfTheMenagerie != null
                            && topCard != null) {
                        if (topCard == card
                                && topCard.isCreature()
                                && topCard.getSpellAbility() != null
                                && topCard.getSpellAbility().spellCanBeActivatedRegularlyNow(controller.getId(), game)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

class VizierOfTheMenagerieManaEffect extends AsThoughEffectImpl implements AsThoughManaEffect {

    public VizierOfTheMenagerieManaEffect() {
        super(AsThoughEffectType.SPEND_OTHER_MANA, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may spend mana as though it were mana of any type to cast creature spells";
    }

    public VizierOfTheMenagerieManaEffect(final VizierOfTheMenagerieManaEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public VizierOfTheMenagerieManaEffect copy() {
        return new VizierOfTheMenagerieManaEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (source.isControlledBy(affectedControllerId)) {
            MageObject mageObject = game.getObject(objectId);
            return mageObject != null
                    && mageObject.isCreature();
        }
        return false;
    }

    @Override
    public ManaType getAsThoughManaType(ManaType manaType, ManaPoolItem mana, UUID affectedControllerId, Ability source, Game game) {
        return mana.getFirstAvailable();
    }
}
