
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Wehk
 */
public final class RummagingWizard extends CardImpl {

    public RummagingWizard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {2}{U}: Look at the top card of your library. You may put that card into your graveyard.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RummagingWizardLookLibraryEffect(), new ManaCostsImpl<>("{2}{U}")));
    }

    private RummagingWizard(final RummagingWizard card) {
        super(card);
    }

    @Override
    public RummagingWizard copy() {
        return new RummagingWizard(this);
    }
}

class RummagingWizardLookLibraryEffect extends OneShotEffect {

    public RummagingWizardLookLibraryEffect() {
        super(Outcome.DrawCard);
        this.staticText = "look at the top card of your library. You may put that card into your graveyard";
    }

    public RummagingWizardLookLibraryEffect(final RummagingWizardLookLibraryEffect effect) {
        super(effect);
    }

    @Override
    public RummagingWizardLookLibraryEffect copy() {
        return new RummagingWizardLookLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            if (controller.getLibrary().hasCards()) {
                Card card = controller.getLibrary().getFromTop(game);
                if (card != null) {
                    CardsImpl cards = new CardsImpl();
                    cards.add(card);
                    controller.lookAtCards("Rummaging Wizard", cards, game);
                    if (controller.chooseUse(Outcome.Neutral, "Put that card into your graveyard?", source, game)) {
                        return controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    }

                }
            }
            return true;
        }
        return false;
    }
}