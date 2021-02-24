package mage.cards.a;

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
import mage.players.Player;

import java.util.UUID;

/**
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
        this.addAbility(new SimpleActivatedAbility(new AethergeodeMinerEffect(), new PayEnergyCost(2)));
    }

    private AethergeodeMiner(final AethergeodeMiner card) {
        super(card);
    }

    @Override
    public AethergeodeMiner copy() {
        return new AethergeodeMiner(this);
    }
}

class AethergeodeMinerEffect extends OneShotEffect {

    AethergeodeMinerEffect() {
        super(Outcome.Neutral);
        this.staticText = "exile {this}, then return it to the battlefield under its owner's control";
    }

    private AethergeodeMinerEffect(final AethergeodeMinerEffect effect) {
        super(effect);
    }

    @Override
    public AethergeodeMinerEffect copy() {
        return new AethergeodeMinerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        Card card = permanent.getMainCard();
        player.moveCards(permanent, Zone.EXILED, source, game);
        player.moveCards(card, Zone.BATTLEFIELD, source, game, false, false, true, null);
        return true;
    }
}
