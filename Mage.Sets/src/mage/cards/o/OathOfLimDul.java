package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.LoseLifeTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedLifeLossValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoUnlessControllerPaysEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class OathOfLimDul extends CardImpl {

    public OathOfLimDul(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        // Whenever you lose life, for each 1 life you lost, sacrifice a permanent other than Oath of Lim-Dul unless you discard a card.
        this.addAbility(new LoseLifeTriggeredAbility(new OathOfLimDulEffect()));

        // {B}{B}: Draw a card.
        this.addAbility(new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{B}{B}")));

    }

    private OathOfLimDul(final OathOfLimDul card) {
        super(card);
    }

    @Override
    public OathOfLimDul copy() {
        return new OathOfLimDul(this);
    }
}

class OathOfLimDulEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("controlled permanent other than {this} to sacrifice");

    static {
        filter.add(AnotherPredicate.instance);
    }

    OathOfLimDulEffect() {
        super(Outcome.Detriment);
        staticText = "for each 1 life you lost, sacrifice a permanent other than {this} unless you discard a card";
    }

    private OathOfLimDulEffect(final OathOfLimDulEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int amountDamage = SavedLifeLossValue.MANY.calculate(game, source, this);
        Player controller = game.getPlayer(source.getControllerId());
        if (amountDamage <= 0 || controller == null) {
            return false;
        }
        boolean didSomething = false;
        for (int i = 0; i < amountDamage; ++i) {
            didSomething |= new DoUnlessControllerPaysEffect(
                    new SacrificeControllerEffect(StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT, 1, ""),
                    new DiscardCardCost()
            ).apply(game, source);
        }
        return didSomething;
    }

    @Override
    public OathOfLimDulEffect copy() {
        return new OathOfLimDulEffect(this);
    }

}
