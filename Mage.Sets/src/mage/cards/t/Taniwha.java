
package mage.cards.t;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PhaseOutAllEffect;
import mage.abilities.keyword.PhasingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author fireshoes
 */
public final class Taniwha extends CardImpl {

    public Taniwha(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Phasing
        this.addAbility(PhasingAbility.getInstance());

        // At the beginning of your upkeep, all lands you control phase out.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new TaniwhaEffect(), TargetController.YOU, false));
    }

    private Taniwha(final Taniwha card) {
        super(card);
    }

    @Override
    public Taniwha copy() {
        return new Taniwha(this);
    }
}

class TaniwhaEffect extends OneShotEffect {

    public TaniwhaEffect() {
        super(Outcome.Detriment);
        this.staticText = "all lands you control phase out";
    }

    public TaniwhaEffect(final TaniwhaEffect effect) {
        super(effect);
    }

    @Override
    public TaniwhaEffect copy() {
        return new TaniwhaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            List<UUID> permIds = new ArrayList<>();
            for (Permanent permanent : game.getBattlefield().getActivePermanents(new FilterControlledLandPermanent(), controller.getId(), game)) {
                permIds.add(permanent.getId());
            }
            return new PhaseOutAllEffect(permIds).apply(game, source);
        }
        return false;
    }
}
