package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.TokenImpl;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author fireshoes
 */
public final class NoyanDarRoilShaper extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("instant or sorcery card");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public NoyanDarRoilShaper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you cast an instant or sorcery spell, you may put three +1/+1 counters on target land you control. 
        // If you do, that land becomes a 0/0 Elemental creature with haste that's still a land.
        Ability ability = new SpellCastControllerTriggeredAbility(new NoyanDarEffect(), filter, true);
        ability.addTarget(new TargetControlledPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    private NoyanDarRoilShaper(final NoyanDarRoilShaper card) {
        super(card);
    }

    @Override
    public NoyanDarRoilShaper copy() {
        return new NoyanDarRoilShaper(this);
    }
}

class NoyanDarEffect extends OneShotEffect {

    public NoyanDarEffect() {
        super(Outcome.BoostCreature);
        this.staticText = "put three +1/+1 counters on target land you control. If you do, that land becomes a 0/0 Elemental creature with haste that's still a land.";
    }

    public NoyanDarEffect(final NoyanDarEffect effect) {
        super(effect);
    }

    @Override
    public NoyanDarEffect copy() {
        return new NoyanDarEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = null;
        for (Target target : source.getTargets()) {
            targetId = target.getFirstTarget();
        }
        if (targetId != null) {
            FixedTarget fixedTarget = new FixedTarget(targetId, game);
            ContinuousEffect continuousEffect = new BecomesCreatureTargetEffect(new AwakenElementalToken(), false, true, Duration.EndOfGame);
            continuousEffect.setTargetPointer(fixedTarget);
            game.addEffect(continuousEffect, source);
            Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance(3));
            effect.setTargetPointer(fixedTarget);
            return effect.apply(game, source);
        }
        return true;
    }
}

class AwakenElementalToken extends TokenImpl {

    public AwakenElementalToken() {
        super("", "0/0 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        this.addAbility(HasteAbility.getInstance());
    }

    public AwakenElementalToken(final AwakenElementalToken token) {
        super(token);
    }

    public AwakenElementalToken copy() {
        return new AwakenElementalToken(this);
    }
}
