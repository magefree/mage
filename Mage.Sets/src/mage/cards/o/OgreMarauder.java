
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class OgreMarauder extends CardImpl {

    public OgreMarauder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Ogre Marauder attacks, it gains "Ogre Marauder can't be blocked" until end of turn unless defending player sacrifices a creature.
        this.addAbility(new AttacksTriggeredAbility(new OgreMarauderEffect(), false));
    }

    private OgreMarauder(final OgreMarauder card) {
        super(card);
    }

    @Override
    public OgreMarauder copy() {
        return new OgreMarauder(this);
    }
}

class OgreMarauderEffect extends OneShotEffect {

    public OgreMarauderEffect() {
        super(Outcome.Benefit);
        this.staticText = "it gains \"{this} can't be blocked\" until end of turn unless defending player sacrifices a creature";
    }

    public OgreMarauderEffect(final OgreMarauderEffect effect) {
        super(effect);
    }

    @Override
    public OgreMarauderEffect copy() {
        return new OgreMarauderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        MageObject sourceObject = game.getObject(source);
        Player defender = game.getPlayer(defendingPlayerId);
        if (defender != null && sourceObject != null) {
            Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
            if (cost.canPay(source, source, defendingPlayerId, game)
                    && defender.chooseUse(Outcome.LoseAbility, "Sacrifice a creature to prevent that " + sourceObject.getLogName() + " can't be blocked?", source, game)) {
                if (!cost.pay(source, game, source, defendingPlayerId, false, null)) {
                    // cost was not payed - so source can't be blocked
                    ContinuousEffect effect = new CantBeBlockedSourceEffect(Duration.EndOfTurn);
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
