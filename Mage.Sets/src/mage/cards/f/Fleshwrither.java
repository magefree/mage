package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author escplan9 (Derek Monturo - dmontur1 at gmail dot com)
 */
public final class Fleshwrither extends CardImpl {

    public Fleshwrither(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Transfigure {1}{B}{B}: Sacrifice this creature: Search your library for a creature card with the same converted mana cost as this 
        // creature and put that card onto the battlefield. Then shuffle your library. Transfigure only as a sorcery.
        this.addAbility(new FleshwritherAbility());
    }

    private Fleshwrither(final Fleshwrither card) {
        super(card);
    }

    @Override
    public Fleshwrither copy() {
        return new Fleshwrither(this);
    }
}

class FleshwritherAbility extends ActivatedAbilityImpl {

    FleshwritherAbility() {
        super(Zone.BATTLEFIELD, new FleshwritherEffect(), new ManaCostsImpl<>("{1}{B}{B}"));
        this.addCost(new SacrificeSourceCost());
        this.setTiming(TimingRule.SORCERY);
    }

    private FleshwritherAbility(final FleshwritherAbility ability) {
        super(ability);
    }

    @Override
    public FleshwritherAbility copy() {
        return new FleshwritherAbility(this);
    }

    @Override
    public String getRule() {
        return "Transfigure {1}{B}{B} <i>({1}{B}{B}, Sacrifice this creature: " +
                "Search your library for a creature card with the same mana value as this creature, " +
                "put that card onto the battlefield, then shuffle. Transfigure only as a sorcery.)</i>";
    }
}

class FleshwritherEffect extends OneShotEffect {

    FleshwritherEffect() {
        super(Outcome.Benefit);
    }

    private FleshwritherEffect(final FleshwritherEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = source.getSourcePermanentOrLKI(game);
        if (sourcePermanent == null || controller == null) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard("creature card with mana value " + sourcePermanent.getManaValue());
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, sourcePermanent.getManaValue()));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        controller.searchLibrary(target, source, game);
        controller.moveCards(controller.getLibrary().getCard(target.getFirstTarget(), game), Zone.BATTLEFIELD, source, game);
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public FleshwritherEffect copy() {
        return new FleshwritherEffect(this);
    }
}
