package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FallersFaithful extends CardImpl {

    public FallersFaithful(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When this creature enters, destroy up to one other target creature. If that creature wasn't dealt damage this turn, its controller draws two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FallersFaithfulEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_ANOTHER_TARGET_CREATURE));
        this.addAbility(ability);
    }

    private FallersFaithful(final FallersFaithful card) {
        super(card);
    }

    @Override
    public FallersFaithful copy() {
        return new FallersFaithful(this);
    }
}

class FallersFaithfulEffect extends OneShotEffect {

    FallersFaithfulEffect() {
        super(Outcome.Benefit);
        staticText = "destroy up to one other target creature. If that creature " +
                "wasn't dealt damage this turn, its controller draws two cards";
    }

    private FallersFaithfulEffect(final FallersFaithfulEffect effect) {
        super(effect);
    }

    @Override
    public FallersFaithfulEffect copy() {
        return new FallersFaithfulEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        boolean notDamagedThisTurn = permanent.getDealtDamageByThisTurn().isEmpty();
        permanent.destroy(source, game);
        if (notDamagedThisTurn) {
            game.processAction();
            Player player = game.getPlayer(permanent.getControllerId());
            if (player != null) {
                player.drawCards(2, source, game);
            }
        }
        return true;
    }
}
