
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author cbt33
 */
public final class PardicMiner extends CardImpl {

    public PardicMiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.DWARF);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Sacrifice Pardic Miner: Target player can't play lands this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new PardicMinerEffect(), new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private PardicMiner(final PardicMiner card) {
        super(card);
    }

    @Override
    public PardicMiner copy() {
        return new PardicMiner(this);
    }
}

class PardicMinerEffect extends ContinuousRuleModifyingEffectImpl {

    public PardicMinerEffect() {
        super(Duration.EndOfTurn, Outcome.Detriment);
        staticText = "Target player can't play lands this turn.";
    }

    public PardicMinerEffect(final PardicMinerEffect effect) {
        super(effect);
    }

    @Override
    public PardicMinerEffect copy() {
        return new PardicMinerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't play lands this turn (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.PLAY_LAND && event.getPlayerId().equals(source.getFirstTarget())) {
            return true;
        }
        return false;
    }

}
