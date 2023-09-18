package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.Target;
import mage.target.common.TargetPermanentAmount;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Broodlord extends CardImpl {

    public Broodlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{3}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Brood Telepathy -- When Broodlord enters the battlefield, distribute X +1/+1 counters among any number of other target creatures you control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new BroodlordEffect());
        ability.addTarget(new TargetPermanentAmount(ManacostVariableValue.ETB, StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability.withFlavorWord("Brood Telepathy"));
    }

    private Broodlord(final Broodlord card) {
        super(card);
    }

    @Override
    public Broodlord copy() {
        return new Broodlord(this);
    }
}

class BroodlordEffect extends OneShotEffect {

    BroodlordEffect() {
        super(Outcome.Benefit);
        staticText = "distribute X +1/+1 counters among any number of other target creatures you control";
    }

    private BroodlordEffect(final BroodlordEffect effect) {
        super(effect);
    }

    @Override
    public BroodlordEffect copy() {
        return new BroodlordEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Target target = source.getTargets().get(0);
        for (UUID targetId : getTargetPointer().getTargets(game, source)) {
            int counters = target.getTargetAmount(targetId);
            if (counters < 1) {
                continue;
            }
            Permanent permanent = game.getPermanent(targetId);
            if (permanent == null) {
                continue;
            }
            permanent.addCounters(CounterType.P1P1.createInstance(counters), source, game);
        }
        return true;
    }
}
