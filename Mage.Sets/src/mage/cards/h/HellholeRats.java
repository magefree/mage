
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author jeffwadsworth
 */
public final class HellholeRats extends CardImpl {

    public HellholeRats(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{R}");
        this.subtype.add(SubType.RAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When Hellhole Rats enters the battlefield, target player discards a card. Hellhole Rats deals damage to that player equal to that card's converted mana cost.
        Ability ability = new EntersBattlefieldTriggeredAbility(new HellholeRatsEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

    }

    public HellholeRats(final HellholeRats card) {
        super(card);
    }

    @Override
    public HellholeRats copy() {
        return new HellholeRats(this);
    }
}

class HellholeRatsEffect extends OneShotEffect {

    public HellholeRatsEffect() {
        super(Outcome.Damage);
        this.staticText = "target player discards a card. {this} deals damage to that player equal to that card's converted mana cost";
    }

    public HellholeRatsEffect(final HellholeRatsEffect effect) {
        super(effect);
    }

    @Override
    public HellholeRatsEffect copy() {
        return new HellholeRatsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = 0;
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (targetPlayer != null) {
            Cards cards = targetPlayer.discard(1, false, source, game);
            if (!cards.isEmpty()) {
                for (Card card : cards.getCards(game)) {
                    damage = card.getConvertedManaCost();
                }
                targetPlayer.damage(damage, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
