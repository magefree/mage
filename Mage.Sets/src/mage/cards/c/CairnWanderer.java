package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.keyword.ChangelingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FearAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.LandwalkAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author psykad
 */
public class CairnWanderer extends CardImpl {
    public CairnWanderer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        
        this.subtype.add("Shapeshifter");
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);        
        
        // Changeling
        this.addAbility(ChangelingAbility.getInstance());
        
        // As long as a creature card with flying is in a graveyard, {this} has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CairnWandererEffect()));
    }

    public CairnWanderer(final CairnWanderer card) {
        super(card);
    }

    @Override
    public CairnWanderer copy() {
        return new CairnWanderer(this);
    }
    
    class CairnWandererEffect extends ContinuousEffectImpl {
        public CairnWandererEffect() {
            super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
            staticText = "As long as a creature card with flying is in a graveyard, {this} has flying. The same is true for fear, first strike, double strike, deathtouch, haste, landwalk, lifelink, protection, reach, trample, shroud, and vigilance.";
        }
        
        public CairnWandererEffect(final CairnWandererEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Permanent perm = game.getPermanent(source.getSourceId());
            
            if (perm == null) return false;
            
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                
                if (player != null) {
                    for (Card card : player.getGraveyard().getCards(game)) {
                        if (card.getCardType().contains(CardType.CREATURE)) {
                            for (Ability ability : card.getAbilities()) {
                                if (ability instanceof FlyingAbility ||
                                    ability instanceof FearAbility ||
                                    ability instanceof FirstStrikeAbility ||
                                    ability instanceof DoubleStrikeAbility ||
                                    ability instanceof DeathtouchAbility ||
                                    ability instanceof HasteAbility ||
                                    ability instanceof LandwalkAbility ||
                                    ability instanceof LifelinkAbility ||
                                    ability instanceof ProtectionAbility ||
                                    ability instanceof ReachAbility ||
                                    ability instanceof TrampleAbility ||
                                    ability instanceof ShroudAbility ||
                                    ability instanceof VigilanceAbility) {
                                    perm.addAbility(ability, game);
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