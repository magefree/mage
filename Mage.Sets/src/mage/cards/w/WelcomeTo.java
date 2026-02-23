package mage.cards.w;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SagaAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.ExileSagaAndReturnTransformedEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.EscapeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.DinosaurToken;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ForEachPlayerTargetsAdjuster;
import mage.target.targetpointer.EachTargetPointer;
import mage.target.targetpointer.FixedTarget;

import java.util.Objects;
import java.util.UUID;

/**
 *
 * @author jimga150
 */
public final class WelcomeTo extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterPermanent("Walls");
    private static final FilterPermanent filterNoncreatureArtifact = new FilterPermanent("noncreature artifact");
    private static final FilterPermanent filterDinosaur = new FilterControlledPermanent("Dinosaur you control");

    static {
        filter.add(SubType.WALL.getPredicate());
        filterNoncreatureArtifact.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filterNoncreatureArtifact.add(CardType.ARTIFACT.getPredicate());
        filterDinosaur.add(SubType.DINOSAUR.getPredicate());
    }

    private static final Hint hint = new ValueHint(
            "Number of Dinosaurs you control", new PermanentsOnBattlefieldCount(filterDinosaur)
    );

    // Based on Azusa's Many Journeys // Likeness of the Seeker, Vronos Masked Inquisitor, In the Darkness Bind Then, 
    public WelcomeTo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{SubType.SAGA}, "{1}{G}{G}",
                "Jurassic Park",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Welcome to . . .
        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getLeftHalfCard());

        // I -- For each opponent, up to one target noncreature artifact they control becomes a 0/4 Wall artifact creature with defender for as long as you control this Saga.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_I, ability -> {
            ability.addEffect(
                    new BecomesCreatureTargetEffect(
                            new CreatureToken(0, 4)
                            .withSubType(SubType.WALL)
                            .withAbility(DefenderAbility.getInstance()),
                            false, false, Duration.WhileControlled
                    ).setText("For each opponent, up to one target noncreature artifact they control becomes " +
                              "a 0/4 Wall artifact creature with defender for as long as you control this Saga."));
            ability.getEffects().setTargetPointer(new EachTargetPointer());
            ability.addTarget(new TargetPermanent(0, 1, filterNoncreatureArtifact));
            ability.setTargetAdjuster(new ForEachPlayerTargetsAdjuster(false, true));
        });

        // II -- Create a 3/3 green Dinosaur creature token with trample. It gains haste until end of turn.
        // Based on Mordor on the March
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_II, new WelcomeToEffect());

        // III -- Destroy all Walls. Exile this Saga, then return it to the battlefield transformed under your control.
        sagaAbility.addChapterEffect(this.getLeftHalfCard(), SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new DestroyAllEffect(filter));
            ability.addEffect(new ExileSagaAndReturnTransformedEffect());
        });

        this.getLeftHalfCard().addAbility(sagaAbility);

        // Jurassic Park
        // Each Dinosaur card in your graveyard has escape. The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.
        // Based on Underworld Breach
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new JurassicParkEffect()));

        // {T}: Add {G} for each Dinosaur you control.
        // Based on Gaea's Cradle
        DynamicManaAbility ability = new DynamicManaAbility(
                Mana.GreenMana(1),
                new PermanentsOnBattlefieldCount(filterDinosaur)
        );
        this.getRightHalfCard().addAbility(ability.addHint(hint));
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

class JurassicParkEffect extends ContinuousEffectImpl {

    JurassicParkEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "Each Dinosaur card in your graveyard has escape. " +
                "The escape cost is equal to the card's mana cost plus exile three other cards from your graveyard.";
    }

    private JurassicParkEffect(final JurassicParkEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        controller
                .getGraveyard()
                .getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .filter(card -> !card.getManaCost().getText().isEmpty()) // card must have a mana cost
                .filter(card -> card.hasSubtype(SubType.DINOSAUR, game))
                .forEach(card -> {
                    Ability ability = new EscapeAbility(card, card.getManaCost().getText(), 3);
                    ability.setSourceId(card.getId());
                    ability.setControllerId(card.getOwnerId());
                    game.getState().addOtherAbility(card, ability);
                });
        return true;
    }

    @Override
    public JurassicParkEffect copy() {
        return new JurassicParkEffect(this);
    }
}
