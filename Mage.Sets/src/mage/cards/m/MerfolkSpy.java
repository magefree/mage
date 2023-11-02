

package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.IslandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public final class MerfolkSpy extends CardImpl {

    public MerfolkSpy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new IslandwalkAbility());
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new MerfolkSpyEffect(), false, true));
    }

    private MerfolkSpy(final MerfolkSpy card) {
        super(card);
    }

    @Override
    public MerfolkSpy copy() {
        return new MerfolkSpy(this);
    }

}

class MerfolkSpyEffect extends OneShotEffect {

    public MerfolkSpyEffect() {
        super(Outcome.Detriment);
        staticText = "that player reveals a card at random from their hand";
    }

    private MerfolkSpyEffect(final MerfolkSpyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        if (player != null && !player.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            revealed.add(player.getHand().getRandom(game));
            player.revealCards("Merfolk Spy", revealed, game);
            return true;
        }
        return false;
    }

    @Override
    public MerfolkSpyEffect copy() {
        return new MerfolkSpyEffect(this);
    }

}