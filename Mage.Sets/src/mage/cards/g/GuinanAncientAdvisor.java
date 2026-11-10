package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.EnteredThisTurnPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.UUID;

/**
 * @author muz
 */
public final class GuinanAncientAdvisor extends CardImpl {

    public GuinanAncientAdvisor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EL_AURIAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // {1}, {T}: Put a +1/+1 counter on each creature you control that entered this turn. If you put two or more +1/+1 counters on creatures this way, draw a card.
        Ability ability = new SimpleActivatedAbility(new GuinanAncientAdvisorEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private GuinanAncientAdvisor(final GuinanAncientAdvisor card) {
        super(card);
    }

    @Override
    public GuinanAncientAdvisor copy() {
        return new GuinanAncientAdvisor(this);
    }
}

class GuinanAncientAdvisorEffect extends OneShotEffect {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(EnteredThisTurnPredicate.instance);
    }

    GuinanAncientAdvisorEffect() {
        super(Outcome.BoostCreature);
        staticText = "put a +1/+1 counter on each creature you control that entered this turn. "
            + "If you put two or more +1/+1 counters on creatures this way, draw a card";
    }

    private GuinanAncientAdvisorEffect(final GuinanAncientAdvisorEffect effect) {
        super(effect);
    }

    @Override
    public GuinanAncientAdvisorEffect copy() {
        return new GuinanAncientAdvisorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        int countersAdded = 0;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            int countersBefore = permanent.getCounters(game).getCount(CounterType.P1P1);
            permanent.addCounters(CounterType.P1P1.createInstance(), source, game);
            countersAdded += permanent.getCounters(game).getCount(CounterType.P1P1) - countersBefore;
        }
        if (countersAdded >= 2) {
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
