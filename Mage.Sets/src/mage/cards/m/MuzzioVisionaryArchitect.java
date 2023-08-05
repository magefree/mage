
package mage.cards.m;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterArtifactPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
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

        // {3}{U}, {tap}: Look at the top X cards of your library, where X is the highest converted mana cost among artifacts you control. You may reveal an artifact card from among them and put it onto the battlefield. Put the rest on the bottom of your library in any order.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new MuzzioVisionaryArchitectEffect(), new ManaCostsImpl<>("{3}{U}"));
        ability.addCost(new TapSourceCost());
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

    public MuzzioVisionaryArchitectEffect() {
        super(Outcome.Benefit);
        this.staticText = "look at the top X cards of your library, where X is the highest mana value among artifacts you control. You may put an artifact card from among them onto the battlefield. Put the rest on the bottom of your library in any order";
    }

    public MuzzioVisionaryArchitectEffect(final MuzzioVisionaryArchitectEffect effect) {
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

        int highCMC = 0;
        List<Permanent> controlledArtifacts = game.getBattlefield().getAllActivePermanents(new FilterArtifactPermanent(), controller.getId(), game);
        for (Permanent permanent : controlledArtifacts) {
            if (permanent.getSpellAbility() != null) {
                int cmc = permanent.getSpellAbility().getManaCosts().manaValue();
                if (cmc > highCMC) {
                    highCMC = cmc;
                }
            }
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, highCMC));
        controller.lookAtCards(source, null, cards, game);
        if (!cards.isEmpty()) {
            TargetCard target = new TargetCard(Zone.LIBRARY, new FilterArtifactCard("artifact card to put onto the battlefield"));
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
