
package mage.cards.f;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.TimingRule;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Fleshwrither extends CardImpl {

    public Fleshwrither(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Transfigure {1}{B}{B}: Sacrifice this creature: Search your library for a creature card with the same converted mana cost as this 
        // creature and put that card onto the battlefield. Then shuffle your library. Transfigure only as a sorcery.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new FleshwritherEffect(), new ManaCostsImpl("{1}{B}{B}"));
        ability.addCost(new SacrificeSourceCost());  
        ability.setTiming(TimingRule.SORCERY);
        this.addAbility(ability);
    }

    public Fleshwrither(final Fleshwrither card) {
        super(card);
    }

    @Override
    public Fleshwrither copy() {
        return new Fleshwrither(this);
    }
}

class FleshwritherEffect extends OneShotEffect {

    FleshwritherEffect() {
        super(Outcome.Benefit);
        staticText = "Transfigure: Sacrifice this creature: Search your library for a creature card with the same converted mana cost as this creature and put that card onto the battlefield";
    }

    FleshwritherEffect(final FleshwritherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            FilterCreatureCard filter = new FilterCreatureCard("creature with converted mana cost " + sourceObject.getConvertedManaCost());
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, sourceObject.getConvertedManaCost()));
            TargetCardInLibrary target = new TargetCardInLibrary(1, filter);
            if (controller.searchLibrary(target, game)) {
                if (!target.getTargets().isEmpty()) {
                    Cards chosen = new CardsImpl(target.getTargets());
                    controller.moveCards(chosen, Zone.BATTLEFIELD, source, game);
                }
            }
            controller.shuffleLibrary(source, game);
            return true;
        }

        return false;
    }

    @Override
    public FleshwritherEffect copy() {
        return new FleshwritherEffect(this);
    }
}
