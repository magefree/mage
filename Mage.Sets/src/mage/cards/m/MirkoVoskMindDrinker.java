
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MirkoVoskMindDrinker extends CardImpl {

    public MirkoVoskMindDrinker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{B}");
        this.subtype.add(SubType.VAMPIRE);
        this.supertype.add(SuperType.LEGENDARY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Whenever Mirko Vosk, Mind Drinker deals combat damage to a player, that player reveals cards from the top of their library until they reveal four land cards, then puts those cards into their graveyard.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MirkoVoskMindDrinkerEffect(), false, true));
    }

    private MirkoVoskMindDrinker(final MirkoVoskMindDrinker card) {
        super(card);
    }

    @Override
    public MirkoVoskMindDrinker copy() {
        return new MirkoVoskMindDrinker(this);
    }

}

class MirkoVoskMindDrinkerEffect extends OneShotEffect {

    public MirkoVoskMindDrinkerEffect() {
        super(Outcome.Benefit);
        this.staticText = "that player reveals cards from the top of their library until they reveal four land cards, then puts those cards into their graveyard";
    }

    public MirkoVoskMindDrinkerEffect(final MirkoVoskMindDrinkerEffect effect) {
        super(effect);
    }

    @Override
    public MirkoVoskMindDrinkerEffect copy() {
        return new MirkoVoskMindDrinkerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (player == null) {
            return false;
        }
        int landsToReveal = 4;
        Cards cards = new CardsImpl();
        for (Card card : player.getLibrary().getCards(game)) {
            if (card != null) {
                cards.add(card);
                if (card.isLand(game) && --landsToReveal < 1) {
                    break;
                }
            }
        }
        player.revealCards(source, "from " + player.getName(), cards, game);
        player.moveCards(cards, Zone.GRAVEYARD, source, game);
        return true;
    }
}
