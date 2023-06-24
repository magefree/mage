package mage.cards.d;

import java.util.UUID;

import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.target.TargetPermanent;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;

/**
 * @author arcox
 */
public final class DemonicEmbrace extends CardImpl {

    public DemonicEmbrace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature gets +3/+1, has flying, and is a Demon in addition to its other types.
        ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEnchantedEffect(3, 1, Duration.WhileOnBattlefield));
        Effect effect = new GainAbilityAttachedEffect(FlyingAbility.getInstance(), AttachmentType.AURA);
        effect.setText(", has flying");
        ability.addEffect(effect);
        effect = new AddCardSubtypeAttachedEffect(SubType.DEMON, Duration.WhileOnBattlefield, AttachmentType.AURA);
        effect.setText(", and is a Demon in addition to its other types");
        ability.addEffect(effect);
        this.addAbility(ability);

        // You may cast Demonic Embrace from your graveyard by paying 3 life and discarding a card in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new DemonicEmbracePlayEffect()));
    }

    private DemonicEmbrace(final DemonicEmbrace card) {
        super(card);
    }

    @Override
    public DemonicEmbrace copy() {
        return new DemonicEmbrace(this);
    }
}


class DemonicEmbracePlayEffect extends AsThoughEffectImpl {

    public DemonicEmbracePlayEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.Benefit);
        staticText = "You may cast {this} from your graveyard by paying 3 life and discarding a card in addition to paying its other costs";
    }

    public DemonicEmbracePlayEffect(final DemonicEmbracePlayEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public DemonicEmbracePlayEffect copy() {
        return new DemonicEmbracePlayEffect(this);
    }

    @Override
    public boolean applies(UUID sourceId, Ability source, UUID affectedControllerId, Game game) {
        if (sourceId.equals(source.getSourceId()) && source.isControlledBy(affectedControllerId)) {
            if (game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
                Player player = game.getPlayer(affectedControllerId);
                if (player != null) {
                    Costs<Cost> costs = new CostsImpl<>();
                    costs.add(new PayLifeCost(3));
                    costs.add(new DiscardCardCost());
                    player.setCastSourceIdWithAlternateMana(sourceId, new ManaCostsImpl<>("{1}{B}{B}"), costs);
                    return true;
                }
            }
        }
        return false;
    }
}
