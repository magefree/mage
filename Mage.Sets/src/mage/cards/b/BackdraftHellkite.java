package mage.cards.b;

import mage.MageInt;
import mage.MageItem;
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

import java.util.List;
import java.util.Objects;
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
        if (!getAffectedObjectsSet()) {
            return;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return;
        }
        player.getGraveyard()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .filter(card -> card.isInstantOrSorcery(game))
                .forEachOrdered(card -> affectedObjectList.add(new MageObjectReference(card, game)));
    }

    @Override
    public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageItem object : affectedObjects) {
            Card card = (Card) object;
            FlashbackAbility ability = new FlashbackAbility(card, card.getManaCost());
            ability.setSourceId(card.getId());
            ability.setControllerId(card.getOwnerId());
            game.getState().addOtherAbility(card, ability);
        }
    }

    @Override
    public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
        for (MageObjectReference mor : affectedObjectList) {
            Card card = mor.getCard(game);
            if (card != null) {
                affectedObjects.add(card);
            }
        }
        return !affectedObjects.isEmpty();
    }
}
