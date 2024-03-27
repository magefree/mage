package mage.cards.a;

import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AridArchway extends CardImpl {

    public AridArchway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.DESERT);

        // Arid Archway enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Arid Archway enters the battlefield, return a land you control to its owner's hand. If another Desert was returned this way, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AridArchwayEffect()));

        // {T}: Add {C}{C}.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(2), new TapSourceCost()));
    }

    private AridArchway(final AridArchway card) {
        super(card);
    }

    @Override
    public AridArchway copy() {
        return new AridArchway(this);
    }
}

class AridArchwayEffect extends OneShotEffect {

    AridArchwayEffect() {
        super(Outcome.Benefit);
        staticText = "return a land you control to its owner's hand. If another Desert was returned this way, surveil 1";
    }

    private AridArchwayEffect(final AridArchwayEffect effect) {
        super(effect);
    }

    @Override
    public AridArchwayEffect copy() {
        return new AridArchwayEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!game.getBattlefield().contains(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND, source, game, 1)) {
            return false;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        if (permanent == null) {
            return false;
        }
        boolean flag = permanent.hasSubtype(SubType.DESERT, game)
                && !new MageObjectReference(permanent, game)
                .refersTo(source, game);
        player.moveCards(permanent, Zone.HAND, source, game);
        if (flag) {
            player.surveil(1, source, game);
        }
        return true;
    }
}
