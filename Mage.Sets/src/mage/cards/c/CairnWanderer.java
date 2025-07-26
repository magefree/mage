
package mage.cards.c;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

import java.util.*;

/**
 *
 * @author psykad
 */
public final class CairnWanderer extends CardImpl {

    public CairnWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Changeling
        this.addAbility(new ChangelingAbility());

        // As long as a creature card with flying is in a graveyard, Cairn Wanderer has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.
        this.addAbility(new SimpleStaticAbility(new CairnWandererEffect()));
    }

    private CairnWanderer(final CairnWanderer card) {
        super(card);
    }

    @Override
    public CairnWanderer copy() {
        return new CairnWanderer(this);
    }

    static class CairnWandererEffect extends ContinuousEffectImpl {

        private static final Set<Class<? extends Ability>> KEYWORD_ABILITIES = new HashSet<>(Arrays.asList(
                FlyingAbility.class,
                FearAbility.class,
                FirstStrikeAbility.class,
                DoubleStrikeAbility.class,
                DeathtouchAbility.class,
                HasteAbility.class,
                LandwalkAbility.class,
                LifelinkAbility.class,
                ProtectionAbility.class,
                ReachAbility.class,
                TrampleAbility.class,
                ShroudAbility.class,
                VigilanceAbility.class
        ));

        public CairnWandererEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            this.addDependedToType(DependencyType.AddingAbility);
            staticText = "As long as a creature card with flying is in a graveyard, {this} has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.";
        }

        private CairnWandererEffect(final CairnWandererEffect effect) {
            super(effect);
        }

        @Override
        public void applyToObjects(Layer layer, SubLayer sublayer, Ability source, Game game, List<MageItem> affectedObjects) {
            Abilities<Ability> graveyardAbilities = new AbilitiesImpl<>();
            getAbilitiesInGraveyards(game, source, graveyardAbilities);

            for (MageItem object : affectedObjects) {
                Permanent permanent = (Permanent) object;
                for (Ability ability : graveyardAbilities) {
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

        private void getAbilitiesInGraveyards(Game game, Ability source, Abilities<Ability> graveyardAbilities) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player == null) {
                    continue;
                }
                for (Card card : player.getGraveyard().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
                    graveyardAbilities.addAll(card.getAbilities(game));
                }
            }
        }

        private boolean isValidKeywordAbility(Class<? extends Ability> abilityClass) {
            return KEYWORD_ABILITIES.stream()
                    .anyMatch(keywordClass ->
                            keywordClass.isAssignableFrom(abilityClass)
                    );
        }

        @Override
        public CairnWandererEffect copy() {
            return new CairnWandererEffect(this);
        }
    }
}
