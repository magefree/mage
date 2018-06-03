
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class CorruptOfficial extends CardImpl {

    public CorruptOfficial(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MINION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {2}{B}: Regenerate Corrupt Official.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl("{2}{B}")));
        
        // Whenever Corrupt Official becomes blocked, defending player discards a card at random.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new CorruptOfficialDiscardEffect(), false));
    }

    public CorruptOfficial(final CorruptOfficial card) {
        super(card);
    }

    @Override
    public CorruptOfficial copy() {
        return new CorruptOfficial(this);
    }
}

class CorruptOfficialDiscardEffect extends OneShotEffect {

    public CorruptOfficialDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player discards a card at random";
    }

    public CorruptOfficialDiscardEffect(final CorruptOfficialDiscardEffect effect) {
        super(effect);
    }

    @Override
    public CorruptOfficialDiscardEffect copy() {
        return new CorruptOfficialDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (blockingCreature != null) {
            Player opponent = game.getPlayer(blockingCreature.getControllerId());
            if (opponent != null) {
                opponent.discard(1, true, source, game);
                return true;
            }
        }
        return false;
    }
}
