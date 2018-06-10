
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author fireshoes
 */
public final class EpitaphGolem extends CardImpl {

    public EpitaphGolem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{5}");
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // {2}: Put target card from your graveyard on the bottom of your library.
        Ability ability = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new EpitaphGolemGraveyardToLibraryEffect(),
                new ManaCostsImpl("{2}"));
        ability.addTarget(new TargetCardInYourGraveyard());
        this.addAbility(ability);
    }

    public EpitaphGolem(final EpitaphGolem card) {
        super(card);
    }

    @Override
    public EpitaphGolem copy() {
        return new EpitaphGolem(this);
    }
}

class EpitaphGolemGraveyardToLibraryEffect extends OneShotEffect {

    public EpitaphGolemGraveyardToLibraryEffect() {
        super(Outcome.Benefit);
        this.staticText = "Put target card from your graveyard on the bottom of your library";
    }

    public EpitaphGolemGraveyardToLibraryEffect(final EpitaphGolemGraveyardToLibraryEffect effect) {
        super(effect);
    }

    @Override
    public EpitaphGolemGraveyardToLibraryEffect copy() {
        return new EpitaphGolemGraveyardToLibraryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
            return card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, false);
        }
        return false;
    }
}
