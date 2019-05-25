package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.*;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallenShinobi extends CardImpl {

    public FallenShinobi(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Ninjutsu {2}{U}{B}
        this.addAbility(new NinjutsuAbility(new ManaCostsImpl("{2}{U}{B}")));

        // Whenever Fallen Shinobi deals combat damage to a player, that player exiles the top two cards of their library. Until end of turn, you may play those cards without paying their mana cost.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new FallenShinobiEffect(), false, true
        ));
    }

    private FallenShinobi(final FallenShinobi card) {
        super(card);
    }

    @Override
    public FallenShinobi copy() {
        return new FallenShinobi(this);
    }
}

class FallenShinobiEffect extends OneShotEffect {

    FallenShinobiEffect() {
        super(Outcome.Benefit);
        staticText = "that player exiles the top two cards of their library. " +
                "Until end of turn, you may play those cards without paying their mana cost.";
    }

    private FallenShinobiEffect(final FallenShinobiEffect effect) {
        super(effect);
    }

    @Override
    public FallenShinobiEffect copy() {
        return new FallenShinobiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player == null) {
            return false;
        }
        Cards cards = new CardsImpl(player.getLibrary().getTopCards(game, 2));
        player.moveCards(cards, Zone.EXILED, source, game);
        for (Card card : cards.getCards(game)) {
            ContinuousEffect effect = new UrzaLordHighArtificerFromExileEffect();
            effect.setTargetPointer(new FixedTarget(card, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class UrzaLordHighArtificerFromExileEffect extends AsThoughEffectImpl {

    UrzaLordHighArtificerFromExileEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfTurn, Outcome.Benefit);
        staticText = "you may play that card without paying its mana cost";
    }

    private UrzaLordHighArtificerFromExileEffect(final UrzaLordHighArtificerFromExileEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public UrzaLordHighArtificerFromExileEffect copy() {
        return new UrzaLordHighArtificerFromExileEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!affectedControllerId.equals(source.getControllerId())
                || !getTargetPointer().getTargets(game, source).contains(objectId)) {
            return false;
        }
        Card card = game.getCard(objectId);
        if (card == null || card.isLand() || card.getSpellAbility().getCosts() == null) {
            return true;
        }
        Player player = game.getPlayer(affectedControllerId);
        if (player == null) {
            return false;
        }
        player.setCastSourceIdWithAlternateMana(objectId, null, card.getSpellAbility().getCosts());
        return true;
    }
}
