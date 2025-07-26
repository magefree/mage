package mage.cards.d;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.*;

/**
 *
 * @author TheElk801
 */
public final class DeathMaskDuplicant extends CardImpl {

    public DeathMaskDuplicant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Imprint - {1}: Exile target creature card from your graveyard.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect().setToSourceExileZone(true), new GenericManaCost(1));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        ability.setAbilityWord(AbilityWord.IMPRINT);
        this.addAbility(ability);

        // As long as a card exiled with Death-Mask Duplicant has flying, Death-Mask Duplicant has flying. The same is true for fear, first strike, double strike, haste, landwalk, protection, and trample.
        this.addAbility(new SimpleStaticAbility(new DeathMaskDuplicantEffect()));
    }

    private DeathMaskDuplicant(final DeathMaskDuplicant card) {
        super(card);
    }

    @Override
    public DeathMaskDuplicant copy() {
        return new DeathMaskDuplicant(this);
    }

    static class DeathMaskDuplicantEffect extends ContinuousEffectImpl {

        private static final Set<Class<? extends Ability>> KEYWORD_ABILITIES = new HashSet<>(Arrays.asList(
                FlyingAbility.class,
                FearAbility.class,
                FirstStrikeAbility.class,
                DoubleStrikeAbility.class,
                HasteAbility.class,
                LandwalkAbility.class,
                ProtectionAbility.class,
                TrampleAbility.class
        ));

        public DeathMaskDuplicantEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            this.addDependedToType(DependencyType.AddingAbility);
            staticText = "As long as a card exiled with {this} has flying, {this} has flying. The same is true for fear, first strike, double strike, haste, landwalk, protection, and trample";
        }

        private DeathMaskDuplicantEffect(final DeathMaskDuplicantEffect effect) {
            super(effect);
        }

        @Override
        public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
            Set<Ability> exileAbilities = new HashSet<>();

            for (MageItem object : affectedObjects) {
                Permanent permanent = (Permanent) object;
                getAbilitiesInExile(game, source, permanent, exileAbilities);
                for (Ability ability : exileAbilities) {
                    if (isValidKeywordAbility(ability.getClass())) {
                        permanent.addAbility(ability, source.getSourceId(), game);
                    }
                }
            }
        }

        @Override
        public boolean queryAffectedObjects(Layer layer, Ability source, Game game, List<MageItem> affectedObjects) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());

            if (sourcePermanent == null) {
                return false;
            }
            affectedObjects.add(sourcePermanent);
            return true;
        }

        private void getAbilitiesInExile(Game game, Ability source, Permanent sourcePermanent, Set<Ability> exileAbilities) {
            ExileZone exileZone = game.getState().getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), sourcePermanent.getZoneChangeCounter(game)));
            if (exileZone == null || exileZone.isEmpty()) {
                return;
            }
            for (Card card : exileZone.getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
                exileAbilities.addAll(card.getAbilities(game));
            }
        }

        private boolean isValidKeywordAbility(Class<? extends Ability> abilityClass) {
            return KEYWORD_ABILITIES.stream()
                    .anyMatch(keywordClass ->
                            keywordClass.isAssignableFrom(abilityClass)
                    );
        }

        @Override
        public DeathMaskDuplicantEffect copy() {
            return new DeathMaskDuplicantEffect(this);
        }
    }
}
