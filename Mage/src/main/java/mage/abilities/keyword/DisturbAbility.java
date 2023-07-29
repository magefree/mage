package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.cards.Card;
import mage.constants.*;
import mage.game.Game;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * 702.146. Disturb
 * <p>
 * 702.146a Disturb is an ability found on the front face of some transforming double-faced cards
 * (see rule 712, “Double-Faced Cards”). “Disturb [cost]” means “You may cast this card
 * transformed from your graveyard by paying [cost] rather than its mana cost.” See
 * rule 712.4b.
 * <p>
 * 702.146b A resolving transforming double-faced spell that was cast using its disturb
 * ability enters the battlefield with its back face up.
 *
 * @author weirddan455, JayDi85
 */
public class DisturbAbility extends SpellAbility {

    private final String manaCost;
    private SpellAbility spellAbilityToResolve;

    public DisturbAbility(Card card, String manaCost) {
        super(card.getSecondFaceSpellAbility());
        this.newId();

        // getSecondFaceSpellAbility() already verified that second face exists
        this.setCardName(card.getSecondCardFace().getName());

        this.zone = Zone.GRAVEYARD;
        this.spellAbilityType = SpellAbilityType.BASE_ALTERNATE;
        this.setSpellAbilityCastMode(SpellAbilityCastMode.DISTURB);

        this.manaCost = manaCost;
        this.getManaCosts().clear();
        this.getManaCostsToPay().clear();
        this.addManaCost(new ManaCostsImpl<>(manaCost));
        this.addSubAbility(new TransformAbility());
    }

    private DisturbAbility(final DisturbAbility ability) {
        super(ability);
        this.manaCost = ability.manaCost;
        this.spellAbilityToResolve = ability.spellAbilityToResolve;
    }

    @Override
    public DisturbAbility copy() {
        return new DisturbAbility(this);
    }

    @Override
    public boolean activate(Game game, boolean noMana) {
        if (super.activate(game, noMana)) {
            game.getState().setValue(TransformAbility.VALUE_KEY_ENTER_TRANSFORMED + getSourceId(), Boolean.TRUE);
            // TODO: must be removed after transform cards (one side) migrated to MDF engine (multiple sides)
            game.addEffect(new DisturbEffect(), this);
            return true;
        }
        return false;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        if (super.canActivate(playerId, game).canActivate()) {
            Card card = game.getCard(getSourceId());
            if (card != null && game.getState().getZone(card.getId()) == Zone.GRAVEYARD) {
                return card.getSpellAbility().canActivate(playerId, game);
            }
        }
        return ActivationStatus.getFalse();
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        return "Disturb " + this.manaCost
                + " <i>(You may cast this card transformed from your graveyard for its disturb cost.)</i>";
    }
}

class DisturbEffect extends ContinuousEffectImpl {

    public DisturbEffect() {
        super(Duration.WhileOnStack, Layer.CopyEffects_1, SubLayer.CopyEffects_1a, Outcome.BecomeCreature);
        staticText = "";
    }

    private DisturbEffect(final DisturbEffect effect) {
        super(effect);
    }

    @Override
    public DisturbEffect copy() {
        return new DisturbEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getSpell(source.getSourceId());
        if (spell == null || spell.getFromZone() != Zone.GRAVEYARD) {
            return false;
        }

        if (spell.getCard().getSecondCardFace() == null) {
            return false;
        }

        // simulate another side as new card (another code part in spell constructor)
        TransformAbility.transformCardSpellDynamic(spell, spell.getCard().getSecondCardFace(), game);
        return true;
    }
}
