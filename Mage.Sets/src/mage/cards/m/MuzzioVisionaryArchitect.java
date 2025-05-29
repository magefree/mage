
package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.*;
import mage.filter.common.FilterArtifactCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class MuzzioVisionaryArchitect extends CardImpl {

    public MuzzioVisionaryArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {3}{U}, {tap}: Look at the top X cards of your library, where X is the greatest mana value among artifacts you control. You may reveal an artifact card from among them and put it onto the battlefield. Put the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(new MuzzioVisionaryArchitectEffect(), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new TapSourceCost());
        ability.addHint(GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS.getHint());
        this.addAbility(ability);
    }

    private MuzzioVisionaryArchitect(final MuzzioVisionaryArchitect card) {
        super(card);
    }

    @Override
    public MuzzioVisionaryArchitect copy() {
        return new MuzzioVisionaryArchitect(this);
    }
}

class MuzzioVisionaryArchitectEffect extends OneShotEffect {

    MuzzioVisionaryArchitectEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top X cards of your library, where X is the greatest mana value among artifacts you control. You may put an artifact card from among them onto the battlefield. Put the rest on the bottom of your library in any order";
    }

    private MuzzioVisionaryArchitectEffect(final MuzzioVisionaryArchitectEffect effect) {
        super(effect);
    }

    @Override
    public MuzzioVisionaryArchitectEffect copy() {
        return new MuzzioVisionaryArchitectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int amount = GreatestAmongPermanentsValue.MANAVALUE_CONTROLLED_ARTIFACTS.calculate(game, source, this);
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, amount));
        controller.lookAtCards(source, null, cards, game);
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(0, 1, Zone.LIBRARY, new FilterArtifactCard("artifact card to put onto the battlefield"));
            if (target.canChoose(controller.getId(), source, game) && controller.choose(Outcome.Benefit, cards, target, source, game)) {
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.revealCards(source, new CardsImpl(card), game);
                    cards.remove(card);
                    controller.moveCards(card, Zone.BATTLEFIELD, source, game);
                }
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, true);
        return true;
    }
}
