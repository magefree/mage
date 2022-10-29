package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class PatronOfTheMoon extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.MOONFOLK, "Moonfolk");

    public PatronOfTheMoon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Moonfolk offering (You may cast this card any time you could cast an instant by sacrificing a Moonfolk and paying the difference in mana costs between this and the sacrificed Moonfolk. Mana cost includes color.)
        this.addAbility(new OfferingAbility(filter));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {1}: Put up to two land cards from your hand onto the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PatronOfTheMoonEffect(), new ManaCostsImpl<>("{1}"));
        this.addAbility(ability);

    }

    private PatronOfTheMoon(final PatronOfTheMoon card) {
        super(card);
    }

    @Override
    public PatronOfTheMoon copy() {
        return new PatronOfTheMoon(this);
    }
}

class PatronOfTheMoonEffect extends OneShotEffect {

    PatronOfTheMoonEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Put up to two land cards from your hand onto the battlefield tapped";
    }

    PatronOfTheMoonEffect(final PatronOfTheMoonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCard target = new TargetCardInHand(0, 2, new FilterLandCard("up to two land cards to put onto the battlefield tapped"));
            controller.chooseTarget(outcome, controller.getHand(), target, source, game);
            return controller.moveCards(new CardsImpl(target.getTargets()).getCards(game),
                    Zone.BATTLEFIELD, source, game, true, false, false, null);
        }
        return false;
    }

    @Override
    public PatronOfTheMoonEffect copy() {
        return new PatronOfTheMoonEffect(this);
    }

}
