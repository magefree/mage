
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.*;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

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
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CairnWandererEffect()));
    }

    private CairnWanderer(final CairnWanderer card) {
        super(card);
    }

    @Override
    public CairnWanderer copy() {
        return new CairnWanderer(this);
    }

    static class CairnWandererEffect extends ContinuousEffectImpl {

        public CairnWandererEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            this.addDependedToType(DependencyType.AddingAbility);
            staticText = "As long as a creature card with flying is in a graveyard, {this} has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.";
        }

        private CairnWandererEffect(final CairnWandererEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());

            if (sourcePermanent == null) {
                return false;
            }

            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);

                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(game)) {
                        if (card.isCreature(game)) {
                            for (Ability ability : card.getAbilities(game)) {
                                if (ability instanceof MageSingleton) {
                                    if (ability instanceof FlyingAbility
                                            || ability instanceof FearAbility
                                            || ability instanceof FirstStrikeAbility
                                            || ability instanceof DoubleStrikeAbility
                                            || ability instanceof DeathtouchAbility
                                            || ability instanceof HasteAbility
                                            || ability instanceof LifelinkAbility
                                            || ability instanceof ReachAbility
                                            || ability instanceof TrampleAbility
                                            || ability instanceof ShroudAbility
                                            || ability instanceof VigilanceAbility) {
                                        sourcePermanent.addAbility(ability, source.getSourceId(), game);
                                    }
                                } else if (ability instanceof ProtectionAbility
                                        || ability instanceof LandwalkAbility) {
                                    sourcePermanent.addAbility(ability, source.getSourceId(), game);
                                }
                            }
                        }
                    }
                }
            }

            return true;
        }

        @Override
        public CairnWandererEffect copy() {
            return new CairnWandererEffect(this);
        }
    }
}
