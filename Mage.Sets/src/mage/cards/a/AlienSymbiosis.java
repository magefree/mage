package mage.cards.a;

import mage.MageIdentifier;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.AddCardSubtypeAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEnchantedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class AlienSymbiosis extends CardImpl {

    public AlienSymbiosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature gets +1/+1, has menace, and is a Symbiote in addition to its other types.
        Ability ability = new SimpleStaticAbility(new BoostEnchantedEffect(1, 1));
        ability.addEffect(new GainAbilityAttachedEffect(new MenaceAbility(), null).concatBy(","));
        ability.addEffect(new AddCardSubtypeAttachedEffect(SubType.SYMBIOTE, AttachmentType.AURA).concatBy(",").setText("and is a Symbiote in addition to its other types"));
        this.addAbility(ability);

        // You may cast this card from your graveyard by discarding a card in addition to paying its other costs.
        this.addAbility(new SimpleStaticAbility(Zone.GRAVEYARD, new AlienSymbiosisGraveyardEffect()));
    }

    private AlienSymbiosis(final AlienSymbiosis card) {
        super(card);
    }

    @Override
    public AlienSymbiosis copy() {
        return new AlienSymbiosis(this);
    }
}
class AlienSymbiosisGraveyardEffect extends AsThoughEffectImpl {

    AlienSymbiosisGraveyardEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.EndOfGame, Outcome.PutCreatureInPlay);
        this.staticText = "You may cast this card from your graveyard by discarding a card in addition to paying its other costs.";
    }

    private AlienSymbiosisGraveyardEffect(final AlienSymbiosisGraveyardEffect effect) {
        super(effect);
    }

    @Override
    public AlienSymbiosisGraveyardEffect copy() {
        return new AlienSymbiosisGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (!objectId.equals(source.getSourceId()) || !source.isControlledBy(affectedControllerId)
                || game.getState().getZone(source.getSourceId()) != Zone.GRAVEYARD) {
            return false;
        }
        Player controller = game.getPlayer(affectedControllerId);
        if (controller != null) {
            Costs<Cost> costs = new CostsImpl<>();
            costs.add(new DiscardCardCost());
            controller.setCastSourceIdWithAlternateMana(objectId, new ManaCostsImpl<>("{1}{B}"), costs,
                    MageIdentifier.AlienSymbiosisAlternateCast);
            return true;
        }
        return false;
    }
}
