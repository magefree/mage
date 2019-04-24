
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OnEventTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author rollergo11
 */
public final class RustElemental extends CardImpl {

    public RustElemental(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // At the beginning of your upkeep, sacrifice an artifact other than Rust Elemental. If you can't, tap Rust Elemental and you lose 4 life.
        this.addAbility(new OnEventTriggeredAbility(GameEvent.EventType.UPKEEP_STEP_PRE, "beginning of your upkeep", new RustElementalEffect(), false));
    }

    public RustElemental(final RustElemental card) {
        super(card);
    }

    @Override
    public RustElemental copy() {
        return new RustElemental(this);
    }
}

class RustElementalEffect extends OneShotEffect {

    private static final FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("artifact");

    static {
        filter.add(new AnotherPredicate());
    }

    public RustElementalEffect() {
        super(Outcome.Damage);
        this.staticText = "Sacrifice an artifact other than {this}. If you can't, tap {this} and you lose 4 life.";
    }

    public RustElementalEffect(final RustElementalEffect effect) {
        super(effect);
    }

    @Override
    public RustElementalEffect copy() {
        return new RustElementalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourceObject = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (sourceObject != null) {
            // create cost for sacrificing an artifact
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null) {
                TargetControlledPermanent target = new TargetControlledPermanent(1, 1, filter, true);
                // if they can pay the cost, then they must pay
                if (target.canChoose(source.getSourceId(), controller.getId(), game)) {
                    controller.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                    Permanent artifactSacrifice = game.getPermanent(target.getFirstTarget());
                    if (artifactSacrifice != null) {
                        // sacrifice the chosen artifact
                        artifactSacrifice.sacrifice(source.getSourceId(), game);
                    }
                } else {
                    sourceObject.tap(game);
                    controller.damage(4, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
