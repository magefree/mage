package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PortalOfSanctuary extends CardImpl {

    public PortalOfSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {1}, {T}: Return target creature you control and each Aura attached to it to their owners' hands. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(
                Zone.BATTLEFIELD, new PortalOfSanctuaryEffect(),
                new GenericManaCost(1), MyTurnCondition.instance
        );
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private PortalOfSanctuary(final PortalOfSanctuary card) {
        super(card);
    }

    @Override
    public PortalOfSanctuary copy() {
        return new PortalOfSanctuary(this);
    }
}

class PortalOfSanctuaryEffect extends OneShotEffect {

    PortalOfSanctuaryEffect() {
        super(Outcome.Benefit);
        staticText = "Return target creature you control and each Aura attached to it to their owners' hands";
    }

    private PortalOfSanctuaryEffect(final PortalOfSanctuaryEffect effect) {
        super(effect);
    }

    @Override
    public PortalOfSanctuaryEffect copy() {
        return new PortalOfSanctuaryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());
        if (permanent == null || player == null) {
            return false;
        }
        Cards cards = new CardsImpl(permanent);
        permanent
                .getAttachments()
                .stream()
                .map(uuid -> game.getPermanent(uuid))
                .filter(perm -> perm != null && perm.hasSubtype(SubType.AURA, game))
                .forEach(perm -> cards.add(perm));
        return player.moveCards(cards, Zone.HAND, source, game);
    }
}