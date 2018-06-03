
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author BetaSteward
 */
public final class Mindshrieker extends CardImpl {

    public Mindshrieker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(FlyingAbility.getInstance());
        // {2}: Target player puts the top card of their library into their graveyard. Mindshrieker gets +X/+X until end of turn, where X is that card's converted mana cost.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MindshriekerEffect(), new ManaCostsImpl("{2}"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public Mindshrieker(final Mindshrieker card) {
        super(card);
    }

    @Override
    public Mindshrieker copy() {
        return new Mindshrieker(this);
    }
}

class MindshriekerEffect extends OneShotEffect {

    public MindshriekerEffect() {
        super(Outcome.BoostCreature);
        staticText = "Target player puts the top card of their library into their graveyard. {this} gets +X/+X until end of turn, where X is that card's converted mana cost";
    }

    public MindshriekerEffect(final MindshriekerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        if (targetPlayer != null) {
            if (targetPlayer.getLibrary().hasCards()) {
                Card card = targetPlayer.getLibrary().getFromTop(game);
                if (card != null) {
                    targetPlayer.moveCards(card, Zone.GRAVEYARD, source, game);
                    int amount = card.getConvertedManaCost();
                    if (amount > 0) {
                        game.addEffect(new BoostSourceEffect(amount, amount, Duration.EndOfTurn), source);
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public MindshriekerEffect copy() {
        return new MindshriekerEffect(this);
    }

}
