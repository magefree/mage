package mage.cards;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.ZoneChangeInfo;
import mage.game.ZonesHandler;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.Arrays;
import java.util.UUID;

/**
 * The alternative characteristics of a prepare card (its "prepare spell"), e.g. Regrowth on
 * Emeritus of Abundance.
 * <p>
 * A prepare spell is never cast directly. While the parent permanent is prepared, its controller may
 * cast a copy of it (CR 722.4). The spell ability is offered from the battlefield (see
 * {@link mage.players.Player#getPlayable}); casting it creates and casts a copy of this card, leaving
 * this blueprint untouched so it can be reused if the permanent becomes prepared again, and
 * unprepares the permanent.
 *
 * @author TheElk801
 */
public class PrepareSpellCard extends CardImpl {

    private final PrepareCard prepareCardParent;

    public PrepareSpellCard(UUID ownerId, CardSetInfo setInfo, String preparationName, CardType[] cardTypes, String costs, PrepareCard prepareCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.PREPARE_SPELL);

        // The prepare spell is cast from the battlefield (on the prepared permanent) rather than the
        // hand, so its spell ability lives in the battlefield zone and checks the prepared designation.
        boolean instantSpeed = Arrays.asList(cardTypes).contains(CardType.INSTANT);
        PrepareSpellAbility newSpellAbility = new PrepareSpellAbility(new ManaCostsImpl<>(costs), preparationName, instantSpeed, prepareCardParent);
        this.replaceSpellAbility(newSpellAbility);
        spellAbility = newSpellAbility;

        this.setName(preparationName);
        this.prepareCardParent = prepareCardParent;
    }

    protected PrepareSpellCard(final PrepareSpellCard card) {
        super(card);
        this.prepareCardParent = card.prepareCardParent;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        // CR 722.4b To cast a copy of a prepare spell, its controller creates a copy of the prepare
        // spell's alternative characteristics and casts that copy. This card (the blueprint) is not
        // moved, so it can be reused if the permanent becomes prepared again.
        Card copiedSpell = game.copyCard(this, ability, controllerId);
        Zone copyFromZone = game.getState().getZone(copiedSpell.getId());
        ZoneChangeEvent event = new ZoneChangeEvent(copiedSpell.getId(), ability, controllerId, copyFromZone, Zone.STACK);
        Spell spell = new Spell(copiedSpell, ability.getSpellAbilityToResolve(game), controllerId, copyFromZone, game);
        spell.setCopy(true, null);
        ZoneChangeInfo.Stack info = new ZoneChangeInfo.Stack(event, spell);
        if (!ZonesHandler.cast(info, ability, game)) {
            return false;
        }

        // CR 722.4a Casting a copy of a prepared permanent's prepare spell unprepares it.
        Permanent permanent = game.getPermanent(prepareCardParent.getId());
        if (permanent != null) {
            permanent.setPrepared(false, game);
        }
        return true;
    }

    @Override
    public PrepareSpellCard copy() {
        return new PrepareSpellCard(this);
    }
}
