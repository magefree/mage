package mage.cards.b;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.FlashbackAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BackdraftHellkite extends CardImpl {

    public BackdraftHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Backdraft Hellkite attacks, each instant and sorcery card in your graveyard gains flashback until end of turn. The flashback cost is equal to its mana cost.
        this.addAbility(new AttacksTriggeredAbility(new BackdraftHellkiteEffect(), false));
    }

    private BackdraftHellkite(final BackdraftHellkite card) {
        super(card);
    }

    @Override
    public BackdraftHellkite copy() {
        return new BackdraftHellkite(this);
    }
}

class BackdraftHellkiteEffect extends ContinuousEffectImpl {

    BackdraftHellkiteEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "each instant and sorcery card in your graveyard gains flashback until end of turn. " +
                "The flashback cost is equal to its mana cost";
    }

    private BackdraftHellkiteEffect(final BackdraftHellkiteEffect effect) {
        super(effect);
    }

    @Override
    public BackdraftHellkiteEffect copy() {
        return new BackdraftHellkiteEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        if (!this.affectedObjectsSet) {
            return;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return;
        }
        player.getGraveyard()
                .stream()
                .map((cardId) -> game.getCard(cardId))
                .filter(card1 -> card1.isInstantOrSorcery(game))
                .forEachOrdered(card -> affectedObjectList.add(new MageObjectReference(card, game)));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.getGraveyard()
                .stream()
                .filter(cardId -> affectedObjectList.contains(new MageObjectReference(cardId, game)))
                .forEachOrdered(cardId -> {
                    Card card = game.getCard(cardId);
                    if (card == null) {
                        return;
                    }
                    FlashbackAbility ability = new FlashbackAbility(card, card.getManaCost());
                    ability.setSourceId(cardId);
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }
}
