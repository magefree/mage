package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AtemsisAllSeeing extends CardImpl {

    public AtemsisAllSeeing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPHINX);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}{U}, {T}: Draw two cards, then discard a card.
        Ability ability = new SimpleActivatedAbility(
                new DrawDiscardControllerEffect(2, 1), new ManaCostsImpl<>("{2}{U}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);

        // Whenever Atemsis, All-Seeing deals damage to an opponent, you may reveal your hand. If cards with at least six different converted mana costs are revealed this way, that player loses the game.
        this.addAbility(new DealsDamageToOpponentTriggeredAbility(
                new AtemsisAllSeeingEffect(), true, false, true
        ));
    }

    private AtemsisAllSeeing(final AtemsisAllSeeing card) {
        super(card);
    }

    @Override
    public AtemsisAllSeeing copy() {
        return new AtemsisAllSeeing(this);
    }
}

class AtemsisAllSeeingEffect extends OneShotEffect {

    AtemsisAllSeeingEffect() {
        super(Outcome.Benefit);
        staticText = "reveal your hand. If cards with at least six different mana values " +
                "are revealed this way, that player loses the game.";
    }

    private AtemsisAllSeeingEffect(final AtemsisAllSeeingEffect effect) {
        super(effect);
    }

    @Override
    public AtemsisAllSeeingEffect copy() {
        return new AtemsisAllSeeingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller == null || opponent == null) {
            return false;
        }
        controller.revealCards(source, controller.getHand(), game);
        if (controller
                .getHand()
                .getCards(game)
                .stream()
                .map(MageObject::getManaValue)
                .distinct()
                .count() > 5) {
            opponent.lost(game);
        }
        return true;
    }
}