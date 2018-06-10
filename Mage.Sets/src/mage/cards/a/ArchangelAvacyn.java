
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextUpkeepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TransformTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class ArchangelAvacyn extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a non-Angel creature you control");

    static {
        filter.add(Predicates.not(new SubtypePredicate(SubType.ANGEL)));
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public ArchangelAvacyn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}{W}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.transformable = true;
        this.secondSideCardClazz = AvacynThePurifier.class;

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Archangel Avacyn enters the battlefield, creatures you control gain indestructible until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityAllEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn,
                        new FilterControlledCreaturePermanent("creatures you control")), false);
        this.addAbility(ability);

        // When a non-Angel creature you control dies, transform Archangel Avacyn at the beginning of the next upkeep.
        this.addAbility(new TransformAbility());
        this.addAbility(new DiesCreatureTriggeredAbility(new ArchangelAvacynEffect(), false, filter));

    }

    public ArchangelAvacyn(final ArchangelAvacyn card) {
        super(card);
    }

    @Override
    public ArchangelAvacyn copy() {
        return new ArchangelAvacyn(this);
    }
}

class ArchangelAvacynEffect extends OneShotEffect {

    private static final String effectText = "transform {this} at the beginning of the next upkeep";

    ArchangelAvacynEffect() {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    ArchangelAvacynEffect(ArchangelAvacynEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObjectIfItStillExists(game);
        if (sourceObject instanceof Permanent) {
            //create delayed triggered ability
            Effect effect = new TransformTargetEffect(false);
            effect.setTargetPointer(new FixedTarget((Permanent) sourceObject, game));
            AtTheBeginOfNextUpkeepDelayedTriggeredAbility delayedAbility = new AtTheBeginOfNextUpkeepDelayedTriggeredAbility(effect);
            game.addDelayedTriggeredAbility(delayedAbility, source);
        }
        return true;

    }

    @Override
    public ArchangelAvacynEffect copy() {
        return new ArchangelAvacynEffect(this);
    }
}
