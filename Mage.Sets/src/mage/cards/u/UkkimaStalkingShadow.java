package mage.cards.u;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.abilities.keyword.PartnerWithAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UkkimaStalkingShadow extends CardImpl {

    public UkkimaStalkingShadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.WHALE);
        this.subtype.add(SubType.WOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Partner with Cazur, Ruthless Stalker
        this.addAbility(new PartnerWithAbility("Cazur, Ruthless Stalker"));

        // Ukkima, Stalking Shadow can't be blocked.
        this.addAbility(new CantBeBlockedSourceAbility());

        // When Ukkima leaves the battlefield, it deals X damage to target player and you gain X life, where X is its power.
        Ability ability = new LeavesBattlefieldTriggeredAbility(new UkkimaStalkingShadowEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private UkkimaStalkingShadow(final UkkimaStalkingShadow card) {
        super(card);
    }

    @Override
    public UkkimaStalkingShadow copy() {
        return new UkkimaStalkingShadow(this);
    }
}

class UkkimaStalkingShadowEffect extends OneShotEffect {

    UkkimaStalkingShadowEffect() {
        super(Outcome.Benefit);
        staticText = "it deals X damage to target player and you gain X life, where X is its power.";
    }

    private UkkimaStalkingShadowEffect(final UkkimaStalkingShadowEffect effect) {
        super(effect);
    }

    @Override
    public UkkimaStalkingShadowEffect copy() {
        return new UkkimaStalkingShadowEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null || permanent.getPower().getValue() <= 0) {
            return false;
        }
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            player.damage(permanent.getPower().getValue(), source.getSourceId(), source, game);
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            controller.gainLife(permanent.getPower().getValue(), game, source);
        }
        return true;
    }
}
