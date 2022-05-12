
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.util.functions.CopyApplier;

/**
 *
 * @author jeffwadsworth
 */
public final class VesuvanDoppelganger extends CardImpl {

    private static final String rule = "You may have {this} enter the battlefield as a copy of any creature on the battlefield except it doesn't copy that creature's color and it has \"At the beginning of your upkeep, you may have this creature become a copy of target creature except it doesn't copy that creature's color and it has this ability.\"";

    public VesuvanDoppelganger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // You may have Vesuvan Doppelganger enter the battlefield as a copy of any creature on the battlefield except it doesn't copy that creature's color and it has "At the beginning of your upkeep, you may have this creature become a copy of target creature except it doesn't copy that creature's color and it has this ability."
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new EntersBattlefieldEffect(new VesuvanDoppelgangerCopyEffect(), rule, true));
        this.addAbility(ability);

    }

    private VesuvanDoppelganger(final VesuvanDoppelganger card) {
        super(card);
    }

    @Override
    public VesuvanDoppelganger copy() {
        return new VesuvanDoppelganger(this);
    }
}

class VesuvanDoppelgangerCopyEffect extends OneShotEffect {

    private static final String rule2 = "At the beginning of your upkeep, you may have this creature become a copy of target creature except it doesn't copy that creature's color and it has this ability.";

    public VesuvanDoppelgangerCopyEffect() {
        super(Outcome.Copy);
    }

    public VesuvanDoppelgangerCopyEffect(final VesuvanDoppelgangerCopyEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = game.getPermanentEntering(source.getSourceId());
        }
        final Permanent sourcePermanent = permanent;
        if (controller != null && sourcePermanent != null) {
            Target target = new TargetPermanent(new FilterCreaturePermanent("target creature (you copy from)"));
            target.setRequired(true);
            if (source instanceof SimpleStaticAbility) {
                target = new TargetPermanent(new FilterCreaturePermanent("creature (you copy from)"));
                target.setRequired(false);
                target.setNotTarget(true);
            }
            if (target.canChoose(source.getControllerId(), source, game)) {
                controller.choose(Outcome.Copy, target, source, game);
                Permanent copyFromPermanent = game.getPermanent(target.getFirstTarget());
                if (copyFromPermanent != null) {
                    game.copyPermanent(copyFromPermanent, sourcePermanent.getId(), source, new CopyApplier() {
                        @Override
                        public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
                            blueprint.getColor().setColor(sourcePermanent.getColor(game));
                            blueprint.getAbilities().add(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD,
                                    new VesuvanDoppelgangerCopyEffect(), TargetController.YOU, true, false, rule2));
                            return true;
                        }
                    });
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public VesuvanDoppelgangerCopyEffect copy() {
        return new VesuvanDoppelgangerCopyEffect(this);
    }
}
