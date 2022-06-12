
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.UntapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author jeffwadsworth
 */
public final class PuresightMerrow extends CardImpl {

    public PuresightMerrow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W/U}{W/U}");
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {WU}, {untap}: Look at the top card of your library. You may exile that card.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PuresightMerrowEffect(), new ManaCostsImpl<>("{W/U}"));
        ability.addCost(new UntapSourceCost());
        this.addAbility(ability);

    }

    private PuresightMerrow(final PuresightMerrow card) {
        super(card);
    }

    @Override
    public PuresightMerrow copy() {
        return new PuresightMerrow(this);
    }
}

class PuresightMerrowEffect extends OneShotEffect {

    public PuresightMerrowEffect() {
        super(Outcome.Detriment);
        staticText = "Look at the top card of your library. You may exile that card";
    }

    public PuresightMerrowEffect(final PuresightMerrowEffect effect) {
        super(effect);
    }

    @Override
    public PuresightMerrowEffect copy() {
        return new PuresightMerrowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            Card card = controller.getLibrary().getFromTop(game);
            if (card != null) {
                Cards cards = new CardsImpl(card);
                controller.lookAtCards("Puresight Merrow", cards, game);
                if (controller.chooseUse(Outcome.Removal, "Exile the card from the top of your library?", source, game)) {
                    controller.moveCardToExileWithInfo(card, source.getSourceId(), sourceObject.getIdName(), source, game, Zone.LIBRARY, true);
                } else {
                    game.informPlayers(controller.getLogName() + " puts the card back on top of their library.");
                }
                return true;
            }
        }
        return false;
    }

}
