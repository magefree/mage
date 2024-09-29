package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.combat.CantAttackBlockTargetEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.effects.common.continuous.LoseAllAbilitiesTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OpportunisticDragon extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Human or artifact an opponent controls");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                SubType.HUMAN.getPredicate()
        ));
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public OpportunisticDragon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Opportunistic Dragon enters the battlefield, choose target Human or artifact an opponent controls.
        // For as long as Opportunistic Dragon remains on the battlefield, gain control of that permanent, it loses all abilities, and it can't attack or block.
        Ability ability = new EntersBattlefieldTriggeredAbility(new OpportunisticDragonControlEffect());
        ability.addEffect(new OpportunisticDragonLoseAbilitiesEffect());
        ability.addEffect(new OpportunisticDragonAttackBlockEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private OpportunisticDragon(final OpportunisticDragon card) {
        super(card);
    }

    @Override
    public OpportunisticDragon copy() {
        return new OpportunisticDragon(this);
    }
}

class OpportunisticDragonControlEffect extends GainControlTargetEffect {

    OpportunisticDragonControlEffect() {
        super(Duration.Custom);
        staticText = "choose target Human or artifact an opponent controls. "
                + "For as long as {this} remains on the battlefield, gain control of that permanent";
    }

    private OpportunisticDragonControlEffect(final OpportunisticDragonControlEffect effect) {
        super(effect);
    }

    @Override
    public OpportunisticDragonControlEffect copy() {
        return new OpportunisticDragonControlEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}

class OpportunisticDragonLoseAbilitiesEffect extends LoseAllAbilitiesTargetEffect {

    OpportunisticDragonLoseAbilitiesEffect() {
        super(Duration.Custom);
        staticText = ", it loses all abilities";
    }

    private OpportunisticDragonLoseAbilitiesEffect(final OpportunisticDragonLoseAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public OpportunisticDragonLoseAbilitiesEffect copy() {
        return new OpportunisticDragonLoseAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            discard();
            return false;
        }
        return super.apply(game, source);
    }
}

class OpportunisticDragonAttackBlockEffect extends CantAttackBlockTargetEffect {

    OpportunisticDragonAttackBlockEffect() {
        super(Duration.Custom);
        staticText = ", and it can't attack or block";
    }

    private OpportunisticDragonAttackBlockEffect(final OpportunisticDragonAttackBlockEffect effect) {
        super(effect);
    }

    @Override
    public OpportunisticDragonAttackBlockEffect copy() {
        return new OpportunisticDragonAttackBlockEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            discard();
            return false;
        }
        return super.applies(permanent, source, game);
    }
}
