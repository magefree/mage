
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class GlintHawk extends CardImpl {

    public GlintHawk(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{W}");
        this.subtype.add(SubType.BIRD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // When Glint Hawk enters the battlefield, sacrifice it unless you return an artifact you control to its owner's hand.

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GlintHawkEffect()));
    }

    public GlintHawk(final GlintHawk card) {
        super(card);
    }

    @Override
    public GlintHawk copy() {
        return new GlintHawk(this);
    }
}

class GlintHawkEffect extends OneShotEffect {

    private static final FilterControlledPermanent filter;
    private static final String effectText = "sacrifice it unless you return an artifact you control to its owner's hand";

    static {
        filter = new FilterControlledPermanent();
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
    }

    GlintHawkEffect ( ) {
        super(Outcome.Sacrifice);
        staticText = effectText;
    }

    GlintHawkEffect ( GlintHawkEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            boolean targetChosen = false;
            TargetPermanent target = new TargetPermanent(1, 1, filter, true);
            if (target.canChoose(controller.getId(), game) && controller.chooseUse(outcome, "Return an artifact you control to its owner's hand?", source, game)) {
                controller.chooseTarget(Outcome.Sacrifice, target, source, game);
                Permanent permanent = game.getPermanent(target.getFirstTarget());
                if (permanent != null) {
                    targetChosen = true;
                    permanent.moveToZone(Zone.HAND, this.getId(), game, false);
                }
            }

            if (!targetChosen) {
                new SacrificeSourceEffect().apply(game, source);
            }
            return true;
        }
        return false;
    }

    @Override
    public GlintHawkEffect copy() {
        return new GlintHawkEffect(this);
    }

}