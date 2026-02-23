package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.token.RedMutantToken;
import mage.game.permanent.token.Token;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTargets;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class OldHobAlleycatBlues extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("attacking creature token");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public OldHobAlleycatBlues(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.MUTANT);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of combat on your turn, create a 2/2 red Mutant creature token.
        // It gains haste until end of turn. Destroy it at the beginning of the next end step.
        this.addAbility(new BeginningOfCombatTriggeredAbility(new OldHobAlleycatBluesEffect()));

        // {1}{W}: Target attacking creature token gains indestructible until end of turn.
        Ability activatedAbility = new SimpleActivatedAbility(new GainAbilityTargetEffect(IndestructibleAbility.getInstance()), new ManaCostsImpl<>("{1}{W}"));
        activatedAbility.addTarget(new TargetPermanent(filter));
        this.addAbility(activatedAbility);
    }

    private OldHobAlleycatBlues(final OldHobAlleycatBlues card) {
        super(card);
    }

    @Override
    public OldHobAlleycatBlues copy() {
        return new OldHobAlleycatBlues(this);
    }
}

class OldHobAlleycatBluesEffect extends OneShotEffect {

    OldHobAlleycatBluesEffect() {
        super(Outcome.Benefit);
        staticText = "create a 2/2 red Mutant creature token. " +
            "It gains haste until end of turn. Destroy it at the beginning of the next end step";
    }

    private OldHobAlleycatBluesEffect(final OldHobAlleycatBluesEffect effect) {
        super(effect);
    }

    @Override
    public OldHobAlleycatBluesEffect copy() {
        return new OldHobAlleycatBluesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Token token = new RedMutantToken();
        token.putOntoBattlefield(1, game, source);
        game.addEffect(new GainAbilityTargetEffect(
            HasteAbility.getInstance(), Duration.EndOfTurn
        ).setTargetPointer(new FixedTargets(token, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
            new DestroyTargetEffect()
                .setTargetPointer(new FixedTargets(token, game))
                .setText("destroy it at the beginning of the next end step"),
            TargetController.YOU
        ).setTriggerPhrase(""), source);
        return true;
    }
}
