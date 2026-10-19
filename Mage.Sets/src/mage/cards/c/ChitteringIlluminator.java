package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AsThoughEffectType;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

public final class ChitteringIlluminator extends CardImpl {

    public ChitteringIlluminator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{G}{G}");
        this.subtype.add(SubType.SQUIRREL);
        this.subtype.add(SubType.GLIMMER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(Zone.LIBRARY, new ChitteringIlluminatorCastEffect(true)));
        this.addAbility(new SimpleStaticAbility(Zone.LIBRARY, new ChitteringIlluminatorLookEffect(true)).setRuleVisible(false));
        this.addAbility(new SimpleStaticAbility(new ChitteringIlluminatorCastEffect(false)));
        this.addAbility(new SimpleStaticAbility(new ChitteringIlluminatorLookEffect(false)).setRuleVisible(false));
    }

    private ChitteringIlluminator(final ChitteringIlluminator card) {
        super(card);
    }

    @Override
    public ChitteringIlluminator copy() {
        return new ChitteringIlluminator(this);
    }
}

class ChitteringIlluminatorCastEffect extends AsThoughEffectImpl {

    ChitteringIlluminatorCastEffect(boolean fromLibrary) {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = fromLibrary
                ? "as long as {this} is at the top of your library, you may look at it any time and you may cast it"
                : "as long as the top card of your library is a creature card, you may look at it any time and you may cast it";
    }

    private ChitteringIlluminatorCastEffect(final ChitteringIlluminatorCastEffect effect) {
        super(effect);
    }

    @Override
    public ChitteringIlluminatorCastEffect copy() {
        return new ChitteringIlluminatorCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return false;
    }

    @Override
    public boolean applies(UUID objectId, Ability affectedAbility, Ability source, Game game, UUID playerId) {
        Player player = game.getPlayer(playerId);
        Card top = player == null ? null : player.getLibrary().getFromTop(game);
        if (!source.isControlledBy(playerId) || top == null || !(affectedAbility instanceof SpellAbility)
                || !top.getId().equals(CardUtil.getMainCardId(game, objectId))) {
            return false;
        }
        // The library ability permits only this copy; the battlefield ability checks the top card, not the spell's face.
        return game.getState().getZone(source.getSourceId()) == Zone.LIBRARY
                ? top.getId().equals(source.getSourceId()) : top.isCreature(game);
    }
}

class ChitteringIlluminatorLookEffect extends LookAtTopCardOfLibraryAnyTimeEffect {

    private final boolean fromLibrary;

    ChitteringIlluminatorLookEffect(boolean fromLibrary) {
        super(Duration.EndOfGame);
        this.fromLibrary = fromLibrary;
        staticText = fromLibrary ? "as long as {this} is at the top of your library, you may look at it any time and you may cast it" : "";
    }

    private ChitteringIlluminatorLookEffect(final ChitteringIlluminatorLookEffect effect) {
        super(effect);
        this.fromLibrary = effect.fromLibrary;
    }

    @Override
    public ChitteringIlluminatorLookEffect copy() {
        return new ChitteringIlluminatorLookEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card top = player == null ? null : player.getLibrary().getFromTop(game);
        return top != null && (fromLibrary ? top.getId().equals(source.getSourceId()) : top.isCreature(game))
                && super.apply(game, source);
    }
}
