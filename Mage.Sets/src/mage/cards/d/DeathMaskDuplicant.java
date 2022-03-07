package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.DependencyType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DeathMaskDuplicantEffect()));
    }

    private DeathMaskDuplicant(final DeathMaskDuplicant card) {
        super(card);
    }

    @Override
    public DeathMaskDuplicant copy() {
        return new DeathMaskDuplicant(this);
    }

    static class DeathMaskDuplicantEffect extends ContinuousEffectImpl {

        public DeathMaskDuplicantEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            this.addDependedToType(DependencyType.AddingAbility);
            staticText = "As long as a card exiled with {this} has flying, {this} has flying. The same is true for fear, first strike, double strike, haste, landwalk, protection, and trample";
        }

        public DeathMaskDuplicantEffect(final DeathMaskDuplicantEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent sourceObject = game.getPermanent(source.getSourceId());

            if (sourceObject == null) {
                return false;
            }

            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    UUID exileId = CardUtil.getExileZoneId(game, source.getSourceId(), sourceObject.getZoneChangeCounter(game));
                    if (exileId != null
                            && game.getState().getExile().getExileZone(exileId) != null
                            && !game.getState().getExile().getExileZone(exileId).isEmpty()) {
                        for (UUID cardId : game.getState().getExile().getExileZone(exileId)) {
                            Card card = game.getCard(cardId);
                            if (card != null && card.isCreature(game)) {
                                for (Ability ability : card.getAbilities(game)) {
                                    if (ability instanceof MageSingleton) {
                                        if (ability instanceof FlyingAbility
                                                || ability instanceof FearAbility
                                                || ability instanceof FirstStrikeAbility
                                                || ability instanceof DoubleStrikeAbility
                                                || ability instanceof HasteAbility
                                                || ability instanceof TrampleAbility) {
                                            sourceObject.addAbility(ability, source.getSourceId(), game);
                                        }
                                    } else if (ability instanceof ProtectionAbility
                                            || ability instanceof LandwalkAbility) {
                                        sourceObject.addAbility(ability, source.getSourceId(), game);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }

        @Override
        public DeathMaskDuplicantEffect copy() {
            return new DeathMaskDuplicantEffect(this);
        }
    }
}
