package mage.cards.r;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.CountersSourceCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseColorEffect;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.EntersBattlefieldWithXCountersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.token.RiptideReplicatorToken;

import java.util.UUID;

/**
 * @author HanClinto
 */
public final class RiptideReplicator extends CardImpl {

    public RiptideReplicator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{X}{4}");

        // As Riptide Replicator enters the battlefield, choose a color and a creature type.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseColorEffect(Outcome.Neutral));
        Effect effect = new ChooseCreatureTypeEffect(Outcome.Neutral);
        effect.setText("and a creature type");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Riptide Replicator enters the battlefield with X charge counters on it.
        this.addAbility(new EntersBattlefieldAbility(new EntersBattlefieldWithXCountersEffect(CounterType.CHARGE.createInstance())));

        // {4}, {T}: Create an X/X creature token of the chosen color and type, where X is the number of charge counters on Riptide Replicator.
        ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new RiptideReplicatorEffect(), new GenericManaCost(4));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private RiptideReplicator(final RiptideReplicator card) {
        super(card);
    }

    @Override
    public RiptideReplicator copy() {
        return new RiptideReplicator(this);
    }
}

class RiptideReplicatorEffect extends OneShotEffect {

    RiptideReplicatorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create an X/X creature token of the chosen color and type, where X is the number of charge counters on {this}.";
    }

    RiptideReplicatorEffect(final RiptideReplicatorEffect effect) {
        super(effect);
    }

    @Override
    public RiptideReplicatorEffect copy() {
        return new RiptideReplicatorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ObjectColor color = (ObjectColor) game.getState().getValue(source.getSourceId() + "_color");
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType == null || color == null) {
            return false;
        }
        int x = (new CountersSourceCount(CounterType.CHARGE)).calculate(game, source, this);
        return new RiptideReplicatorToken(color, subType, x).putOntoBattlefield(1, game, source, source.getControllerId());
    }
}
