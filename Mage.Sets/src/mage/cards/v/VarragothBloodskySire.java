package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.SearchEffect;
import mage.abilities.keyword.BoastAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class VarragothBloodskySire extends CardImpl {

    public VarragothBloodskySire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Boast -- {1}{B}:Target player searches their library for a card, then shuffles their library and puts that card on top of it.
        Ability ability = new BoastAbility(new VarragothBloodskySireEffect(), "{1}{B}");
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private VarragothBloodskySire(final VarragothBloodskySire card) {
        super(card);
    }

    @Override
    public VarragothBloodskySire copy() {
        return new VarragothBloodskySire(this);
    }
}

class VarragothBloodskySireEffect extends SearchEffect {

    public VarragothBloodskySireEffect() {
        super(new TargetCardInLibrary(), Outcome.DrawCard);
        this.staticText = "Target player searches their library for a card, then shuffles and puts that card on top";
    }

    private VarragothBloodskySireEffect(final VarragothBloodskySireEffect effect) {
        super(effect);
    }

    @Override
    public VarragothBloodskySireEffect copy() {
        return new VarragothBloodskySireEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            if (player.searchLibrary(target, source, game)) {
                Cards foundCards = new CardsImpl(target.getTargets());
                player.shuffleLibrary(source, game);
                player.putCardsOnTopOfLibrary(foundCards, game, source, false);
                return true;
            }
            player.shuffleLibrary(source, game);
        }
        return false;
    }
}
