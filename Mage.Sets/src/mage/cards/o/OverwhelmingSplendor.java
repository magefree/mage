
package mage.cards.o;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.ActivatedManaAbilityImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.Optional;
import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class OverwhelmingSplendor extends CardImpl {

    public OverwhelmingSplendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{6}{W}{W}");

        this.subtype.add(SubType.AURA, SubType.CURSE);

        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.LoseAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Creatures enchanted player controls lose all abilities and have base power and toughness 1/1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OverwhelmingSplendorLoseAbilitiesEffect()));

        // Enchanted player can't activate abilities that aren't mana abilities or loyalty abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OverwhelmingSplendorCantActivateEffect()));
    }

    private OverwhelmingSplendor(final OverwhelmingSplendor card) {
        super(card);
    }

    @Override
    public OverwhelmingSplendor copy() {
        return new OverwhelmingSplendor(this);
    }
}

class OverwhelmingSplendorLoseAbilitiesEffect extends ContinuousEffectImpl {

    public OverwhelmingSplendorLoseAbilitiesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.LoseAbility);
        staticText = "Creatures enchanted player controls lose all abilities and have base power and toughness 1/1";
    }

    public OverwhelmingSplendorLoseAbilitiesEffect(final OverwhelmingSplendorLoseAbilitiesEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingSplendorLoseAbilitiesEffect copy() {
        return new OverwhelmingSplendorLoseAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        // In the case that the enchantment is blinked
        Permanent enchantment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (enchantment == null) {
            // It was not blinked, use the standard method
            enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
            if (enchantment == null) {
                return false;
            }
        }

        Player player = game.getPlayer(enchantment.getAttachedTo());
        if (player == null) {
            return false;
        }

        for (Permanent permanent : game.getState().getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, player.getId(), game)) {
            switch (layer) {
                case AbilityAddingRemovingEffects_6:
                    permanent.removeAllAbilities(source.getSourceId(), game);
                    break;
                case PTChangingEffects_7:
                    if (sublayer == SubLayer.SetPT_7b) {
                        permanent.getPower().setModifiedBaseValue(1);
                        permanent.getToughness().setModifiedBaseValue(1);
                    }
            }
        }
        return true;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.PTChangingEffects_7;
    }

}

class OverwhelmingSplendorCantActivateEffect extends ContinuousRuleModifyingEffectImpl {

    public OverwhelmingSplendorCantActivateEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Enchanted player can't activate abilities that aren't mana abilities or loyalty abilities";
    }

    public OverwhelmingSplendorCantActivateEffect(final OverwhelmingSplendorCantActivateEffect effect) {
        super(effect);
    }

    @Override
    public OverwhelmingSplendorCantActivateEffect copy() {
        return new OverwhelmingSplendorCantActivateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't activate abilities that aren't mana abilities or loyalty abilities (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() != GameEvent.EventType.ACTIVATE_ABILITY) {
            return false;
        }

        Permanent enchantment = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (enchantment == null || !event.getPlayerId().equals(enchantment.getAttachedTo())) {
            return false;
        }

        Optional<Ability> ability = game.getAbility(event.getTargetId(), event.getSourceId());
        return ability.isPresent()
                && !(ability.get() instanceof ActivatedManaAbilityImpl)
                && !(ability.get() instanceof LoyaltyAbility);
    }
}
