
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.PayEnergyCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.GetEnergyCountersControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author fireshoes
 */
public final class AethergeodeMiner extends CardImpl {

    public AethergeodeMiner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Aethergeode Miner attacks, you get {E}{E}.
        this.addAbility(new AttacksTriggeredAbility(new GetEnergyCountersControllerEffect(2), false));

        // Pay {E}{E}: Exile Aethergeode Miner, then return it to the battlefield under its owner's control.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AethergeodeMinerEffect(), new PayEnergyCost(2)));
    }

    public AethergeodeMiner(final AethergeodeMiner card) {
        super(card);
    }

    @Override
    public AethergeodeMiner copy() {
        return new AethergeodeMiner(this);
    }
}

class AethergeodeMinerEffect extends OneShotEffect {

    public AethergeodeMinerEffect() {
        super(Outcome.Neutral);
        this.staticText = "Exile {this}, then return it to the battlefield under its owner's control";
    }

    public AethergeodeMinerEffect(final AethergeodeMinerEffect effect) {
        super(effect);
    }

    @Override
    public AethergeodeMinerEffect copy() {
        return new AethergeodeMinerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            if (permanent.moveToExile(source.getSourceId(), "Aethergeode Miner", source.getSourceId(), game)) {
                Card card = game.getExile().getCard(source.getSourceId(), game);
                if (card != null) {
                    return card.moveToZone(Zone.BATTLEFIELD, source.getSourceId(), game, false);
                }
            }
        }
        return false;
    }
}
