package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreyKnightParagon extends CardImpl {

    public GreyKnightParagon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Rites of Banishment -- When Grey Knight Paragon enters the battlefield, destroy target attacking creature. If that creature is a Demon, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GreyKnightParagonEffect());
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability.withFlavorWord("Rites of Banishment"));
    }

    private GreyKnightParagon(final GreyKnightParagon card) {
        super(card);
    }

    @Override
    public GreyKnightParagon copy() {
        return new GreyKnightParagon(this);
    }
}

class GreyKnightParagonEffect extends OneShotEffect {

    GreyKnightParagonEffect() {
        super(Outcome.Benefit);
        staticText = "destroy target attacking creature. If that creature is a Demon, exile it instead";
    }

    private GreyKnightParagonEffect(final GreyKnightParagonEffect effect) {
        super(effect);
    }

    @Override
    public GreyKnightParagonEffect copy() {
        return new GreyKnightParagonEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null
                && (permanent.hasSubtype(SubType.DEMON, game)
                ? player.moveCards(permanent, Zone.EXILED, source, game)
                : permanent.destroy(source, game));
    }
}
