
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInASingleGraveyard;

/**
 *
 * @author fireshoes
 */
public final class CarrionBeetles extends CardImpl {

    public CarrionBeetles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{B}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {2}{B}, {tap}: Exile up to three target cards from a single graveyard.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CarrionBeetlesExileEffect(), new ManaCostsImpl("{2}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInASingleGraveyard(0, 3, new FilterCard("up to three target cards from a single graveyard")));
        this.addAbility(ability);
    }

    public CarrionBeetles(final CarrionBeetles card) {
        super(card);
    }

    @Override
    public CarrionBeetles copy() {
        return new CarrionBeetles(this);
    }
}

class CarrionBeetlesExileEffect extends OneShotEffect {

    public CarrionBeetlesExileEffect() {
            super(Outcome.Exile);
            this.staticText = "Exile up to three target cards from a single graveyard";
    }

    public CarrionBeetlesExileEffect(final CarrionBeetlesExileEffect effect) {
            super(effect);
    }

    @Override
    public CarrionBeetlesExileEffect copy() {
            return new CarrionBeetlesExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (UUID targetID : source.getTargets().get(0).getTargets()) {
            Card card = game.getCard(targetID);
            if (card != null) {
                card.moveToExile(null, "", source.getSourceId(), game);
            }
        }
        return true;
    }
}
