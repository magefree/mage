package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.CreatureConnivesTriggeredAbility;
import mage.abilities.common.PlanCounterThresholdTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class GloriousPurpose extends CardImpl {

    public GloriousPurpose(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.PLAN);

        // Whenever a creature you control connives, put a +1/+1 counter on that creature and a plan counter on this enchantment.
        Ability ability = new CreatureConnivesTriggeredAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()).withTargetDescription("that creature"),
                SetTargetPointer.PERMANENT
        );
        ability.addEffect(new AddCountersSourceEffect(CounterType.PLAN.createInstance())
                .setText("and a plan counter on this enchantment"));
        this.addAbility(ability);

        // When the sixth plan counter is put on this enchantment, sacrifice it. If you do, exile the top four cards of your library. You may cast any number of spells from among them without paying their mana costs. Put the rest into your hand.
        this.addAbility(new PlanCounterThresholdTriggeredAbility(6, new DoIfCostPaid(
                new GloriousPurposeEffect(), new SacrificeSourceCost().setText("sacrifice it"), null, false
        )));
    }

    private GloriousPurpose(final GloriousPurpose card) {
        super(card);
    }

    @Override
    public GloriousPurpose copy() {
        return new GloriousPurpose(this);
    }
}

class GloriousPurposeEffect extends OneShotEffect {

    GloriousPurposeEffect() {
        super(Outcome.PlayForFree);
        staticText = "exile the top four cards of your library. You may cast any number of spells from among them "
                + "without paying their mana costs. Put the rest into your hand";
    }

    private GloriousPurposeEffect(final GloriousPurposeEffect effect) {
        super(effect);
    }

    @Override
    public GloriousPurposeEffect copy() {
        return new GloriousPurposeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));
        controller.moveCards(cards, Zone.EXILED, source, game);
        game.processAction();
        cards.retainZone(Zone.EXILED, game);
        // cast cards are removed from the list
        CardUtil.castMultipleWithAttributeForFree(controller, source, game, cards, StaticFilters.FILTER_CARD);
        controller.moveCards(cards, Zone.HAND, source, game);
        return true;
    }
}
