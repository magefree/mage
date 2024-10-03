package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author North
 */
public final class SphinxAmbassador extends CardImpl {

    public SphinxAmbassador(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());

        // Whenever Sphinx Ambassador deals combat damage to a player, search that player's library for a card, then that player names a card. If you searched for a creature card that isn't the named card, you may put it onto the battlefield under your control. Then that player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new SphinxAmbassadorEffect(), false, true
        ));
    }

    private SphinxAmbassador(final SphinxAmbassador card) {
        super(card);
    }

    @Override
    public SphinxAmbassador copy() {
        return new SphinxAmbassador(this);
    }
}

class SphinxAmbassadorEffect extends OneShotEffect {

    SphinxAmbassadorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search that player's library for a card, then that player chooses a card name. " +
                "If you searched for a creature card that doesn't have that name, " +
                "you may put it onto the battlefield under your control. Then that player shuffles";
    }

    private SphinxAmbassadorEffect(final SphinxAmbassadorEffect effect) {
        super(effect);
    }

    @Override
    public SphinxAmbassadorEffect copy() {
        return new SphinxAmbassadorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller == null || targetPlayer == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary();
        controller.searchLibrary(target, source, game, targetPlayer.getId());
        Card card = targetPlayer.getLibrary().getCard(target.getFirstTarget(), game);
        String name = ChooseACardNameEffect.TypeOfName.ALL.getChoice(targetPlayer, game, source, false);
        if (card != null
                && card.isCreature(game)
                && !card.hasName(name, game)
                && controller.chooseUse(outcome, "Put " + card.getName() + " onto the battlefield?", source, game)) {
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        targetPlayer.shuffleLibrary(source, game);
        return true;
    }
}
