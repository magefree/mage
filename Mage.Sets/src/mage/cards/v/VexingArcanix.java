package mage.cards.v;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com & L_J
 */
public final class VexingArcanix extends CardImpl {

    public VexingArcanix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {3}, {tap}: Target player chooses a card name, then reveals the top card of their library. If that card has the chosen name, the player puts it into their hand. Otherwise, the player puts it into their graveyard and Vexing Arcanix deals 2 damage to them.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new VexingArcanixEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VexingArcanix(final VexingArcanix card) {
        super(card);
    }

    @Override
    public VexingArcanix copy() {
        return new VexingArcanix(this);
    }

}

class VexingArcanixEffect extends OneShotEffect {

    public VexingArcanixEffect() {
        super(Outcome.DrawCard);
        staticText = "Target player chooses a card name, then reveals the top card of their library. " +
                "If that card has the chosen name, the player puts it into their hand. Otherwise, " +
                "the player puts it into their graveyard and {this} deals 2 damage to them";
    }

    private VexingArcanixEffect(final VexingArcanixEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (sourceObject == null || player == null) {
            return false;
        }
        String cardName = ChooseACardNameEffect.TypeOfName.ALL.getChoice(player, game, source, false);
        Card card = player.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        Cards cards = new CardsImpl(card);
        player.revealCards(sourceObject.getIdName(), cards, game);
        if (CardUtil.haveSameNames(card, cardName, game)) {
            player.moveCards(cards, Zone.HAND, source, game);
        } else {
            player.moveCards(cards, Zone.GRAVEYARD, source, game);
            player.damage(2, source.getSourceId(), source, game);
        }
        return true;
    }

    @Override
    public VexingArcanixEffect copy() {
        return new VexingArcanixEffect(this);
    }
}
