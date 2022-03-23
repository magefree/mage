package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
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
 * @author maurer.it_at_gmail.com
 */
public final class GlintHawk extends CardImpl {

    public GlintHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Glint Hawk enters the battlefield, sacrifice it unless you return an artifact you control to its owner's hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GlintHawkEffect()));
    }

    private GlintHawk(final GlintHawk card) {
        super(card);
    }

    @Override
    public GlintHawk copy() {
        return new GlintHawk(this);
    }
}

class GlintHawkEffect extends OneShotEffect {

    private static final String effectText = "sacrifice it unless you return an artifact you control to its owner's hand";

    GlintHawkEffect() {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    private GlintHawkEffect(final GlintHawkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT);
        target.setNotTarget(true);
        if (target.canChoose(controller.getId(), source, game)
                && controller.chooseUse(outcome, "Return an artifact you control to its owner's hand?", source, game)) {
            controller.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Permanent permanent = game.getPermanent(target.getFirstTarget());
            if (permanent != null) {
                controller.moveCards(permanent, Zone.HAND, source, game);
                return true;
            }
        }
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.sacrifice(source, game);
    }

    @Override
    public GlintHawkEffect copy() {
        return new GlintHawkEffect(this);
    }
}
