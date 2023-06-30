
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class ArjunTheShiftingFlame extends CardImpl {

    public ArjunTheShiftingFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Whenever you cast a spell, put the cards in your hand on the bottom of your library in any order, then draw that many cards.
        this.addAbility(new SpellCastControllerTriggeredAbility(new ArjunTheShiftingFlameEffect(), false));
    }

    private ArjunTheShiftingFlame(final ArjunTheShiftingFlame card) {
        super(card);
    }

    @Override
    public ArjunTheShiftingFlame copy() {
        return new ArjunTheShiftingFlame(this);
    }
}

class ArjunTheShiftingFlameEffect extends OneShotEffect {

    public ArjunTheShiftingFlameEffect() {
        super(Outcome.Neutral);
        staticText = "put the cards in your hand on the bottom of your library in any order, then draw that many cards";
    }

    public ArjunTheShiftingFlameEffect(final ArjunTheShiftingFlameEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        if (you != null) {
            int count = you.getHand().size();
            you.putCardsOnBottomOfLibrary(you.getHand(), game, source, true);
            you.drawCards(count, source, game);
        }
        return true;
    }

    @Override
    public ArjunTheShiftingFlameEffect copy() {
        return new ArjunTheShiftingFlameEffect(this);
    }
}