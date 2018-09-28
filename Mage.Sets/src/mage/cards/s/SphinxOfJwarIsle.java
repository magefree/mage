
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author North
 */
public final class SphinxOfJwarIsle extends CardImpl {

    public SphinxOfJwarIsle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{U}");
        this.subtype.add(SubType.SPHINX);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(ShroudAbility.getInstance());
        // TODO: this should be a static ability
        this.addAbility(new SphinxOfJwarIsleLookAbility());

    }

    public SphinxOfJwarIsle(final SphinxOfJwarIsle card) {
        super(card);
    }

    @Override
    public SphinxOfJwarIsle copy() {
        return new SphinxOfJwarIsle(this);
    }
}

class SphinxOfJwarIsleLookAbility extends ActivatedAbilityImpl {

    public SphinxOfJwarIsleLookAbility() {
        super(Zone.BATTLEFIELD, new SphinxOfJwarIsleEffect(), new GenericManaCost(0));
        this.usesStack = false;
    }

    public SphinxOfJwarIsleLookAbility(SphinxOfJwarIsleLookAbility ability) {
        super(ability);
    }

    @Override
    public SphinxOfJwarIsleLookAbility copy() {
        return new SphinxOfJwarIsleLookAbility(this);
    }

}

class SphinxOfJwarIsleEffect extends OneShotEffect {

    public SphinxOfJwarIsleEffect() {
        super(Outcome.Neutral);        
        this.staticText = "You may look at the top card of your library any time";
    }

    public SphinxOfJwarIsleEffect(final SphinxOfJwarIsleEffect effect) {
        super(effect);
    }

    @Override
    public SphinxOfJwarIsleEffect copy() {
        return new SphinxOfJwarIsleEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }

        Card card = player.getLibrary().getFromTop(game);
        if (card != null) {
            Cards cards = new CardsImpl();
            cards.add(card);
            player.lookAtCards("Sphinx of Jwar Isle", cards, game);
        } else {
            return false;
        }

        return true;
    }
}
