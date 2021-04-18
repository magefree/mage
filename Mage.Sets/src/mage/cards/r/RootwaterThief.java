
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LoneFox

 */
public final class RootwaterThief extends CardImpl {

    public RootwaterThief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {U}: Rootwater Thief gains flying until end of turn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.EndOfTurn), new ManaCostsImpl("{U}")));
        // Whenever Rootwater Thief deals combat damage to a player, you may pay {2}. If you do, search that player's library for a card and exile it, then the player shuffles their library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new RootwaterThiefEffect(), false, true));
    }

    private RootwaterThief(final RootwaterThief card) {
        super(card);
    }

    @Override
    public RootwaterThief copy() {
        return new RootwaterThief(this);
    }
}

class RootwaterThiefEffect extends OneShotEffect {

    RootwaterThiefEffect() {
        super(Outcome.Exile);
        staticText = "you may pay {2}. If you do, search that player's library for a card and exile it, then the player shuffles.";
    }

    RootwaterThiefEffect(final RootwaterThiefEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player damagedPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || damagedPlayer == null) {
            return false;
        }
        String message = "Pay {2} to exile a card from damaged player's library?";
        Cost cost = new ManaCostsImpl("{2}");
        if(controller.chooseUse(Outcome.Benefit, message, source, game) && cost.pay(source, game, source, controller.getId(), false, null))
        {
            TargetCardInLibrary target = new TargetCardInLibrary();
            if (controller.searchLibrary(target, source, game, damagedPlayer.getId())) {
                if (!target.getTargets().isEmpty()) {
                    Card card = damagedPlayer.getLibrary().remove(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCardToExileWithInfo(card, null, "", source, game, Zone.LIBRARY, true);
                    }
                }
            }

            damagedPlayer.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public RootwaterThiefEffect copy() {
        return new RootwaterThiefEffect(this);
    }
}
