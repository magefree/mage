package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author Hiddevb
 */
public final class RaffinesGuidance extends CardImpl {

    public RaffinesGuidance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(1, 1, Duration.WhileOnBattlefield)));

        // You may cast Raffine’s Guidance from your graveyard by paying {2}{W} instead of its mana cost.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new RafinnesGuidancePlayEffect()));
    }

    private RaffinesGuidance(final RaffinesGuidance card) {
        super(card);
    }

    @Override
    public RaffinesGuidance copy() {
        return new RaffinesGuidance(this);
    }
}

class RafinnesGuidancePlayEffect extends AsThoughEffectImpl {

    public RafinnesGuidancePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard by paying {2}{W} rather than paying its mana cost.";
    }

    public RafinnesGuidancePlayEffect(final RafinnesGuidancePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                Player player = game.getPlayer(affectedControllerId);
                if (player != null) {
                    Costs<Cost> costs = new CostsImpl<>();
                    player.setCastSourceIdWithAlternateMana(sourceId, new ManaCostsImpl<>("{2}{W}"), costs);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public RafinnesGuidancePlayEffect copy() {
        return new RafinnesGuidancePlayEffect(this);
    }


}
