package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.DinosaurToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.EachOpponentPermanentTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 *
 * @author jimga150
 */
public final class WelcomeTo extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Walls");
    private static final FilterPermanent filterNoncreatureArtifact = new FilterPermanent("noncreature artifact");

    static {
        filter.add(SubType.WALL.getPredicate());
        filterNoncreatureArtifact.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filterNoncreatureArtifact.add(CardType.ARTIFACT.getPredicate());
    }

    // Based on Azusa's Many Journeys // Likeness of the Seeker, Vronos Masked Inquisitor, In the Darkness Bind Then, 
    public WelcomeTo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");
        
        this.subtype.add(SubType.SAGA);
        this.secondSideCardClazz = mage.cards.j.JurassicPark.class;

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I -- For each opponent, up to one target noncreature artifact they control becomes a 0/4 Wall artifact creature with defender for as long as you control this Saga.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(
                    new BecomesCreatureTargetEffect(
                            new CreatureToken(0, 4)
                            .withSubType(SubType.WALL)
                            .withAbility(DefenderAbility.getInstance()),
                            false, false, Duration.WhileControlled
                    ).setText("For each opponent, up to one target noncreature artifact they control becomes " +
                              "a 0/4 Wall artifact creature with defender for as long as you control this Saga."));
            ability.getEffects().setTargetPointer(new EachTargetPointer());
            ability.setTargetAdjuster(new EachOpponentPermanentTargetsAdjuster());
            ability.addTarget(new TargetPermanent(0, 1, filterNoncreatureArtifact));
        });

        // II -- Create a 3/3 green Dinosaur creature token with trample. It gains haste until end of turn.
        // Based on Mordor on the March
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_II, new WelcomeToEffect());

        // III -- Destroy all Walls. Exile this Saga, then return it to the battlefield transformed under your control.
        this.addAbility(new TransformAbility());
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new DestroyAllEffect(filter));
            ability.addEffect(new ExileSagaAndReturnTransformedEffect());
        });

        this.addAbility(sagaAbility);
    }

    private WelcomeTo(final WelcomeTo card) {
        super(card);
    }

    @Override
    public WelcomeTo copy() {
        return new WelcomeTo(this);
    }
}

// Based on Mordor on the March
class WelcomeToEffect extends OneShotEffect {

    WelcomeToEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Create a 3/3 green Dinosaur creature token with trample. It gains haste until end of turn.";
    }

    private WelcomeToEffect(final WelcomeToEffect effect) {
        super(effect);
    }

    @Override
    public WelcomeToEffect copy() {
        return new WelcomeToEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        CreateTokenEffect effect = new CreateTokenEffect(new DinosaurToken());
        effect.apply(game, source);
        effect.getLastAddedTokenIds().forEach(permanentID -> {
            ContinuousEffect continuousEffect = new GainAbilityTargetEffect(
                    HasteAbility.getInstance(), Duration.EndOfTurn
            );
            continuousEffect.setTargetPointer(new FixedTarget(permanentID));
            game.addEffect(continuousEffect, source);
        });
        return true;
    }

}
