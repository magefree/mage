package mage.cards;

import mage.constants.CardType;
import mage.abilities.SpellAbility;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.keyword.PrepareReminderAbility;

import java.util.UUID;

/*
722. Preparation Cards
722.1. Preparation cards have a two-part card frame, with a smaller frame inset within their text box.
722.2. The text that appears in the inset frame on the right defines alternative characteristics that the
object may have while it’s a spell. The card’s normal characteristics appear as usual, although with
a smaller text box on the left.
722.2a The inset frame of a preparation card is called a “prepare spell.” If a rule or effect refers to a
card, spell, or permanent that has a prepare spell, it refers to an object for which these
alternative characteristics exist, even if the object currently doesn’t use them.
722.2b The existence and values of these alternative characteristics are part of the object’s copiable
values.
722.2c Although preparation cards are printed with multiple sets of characteristics, each preparation
card is only one card. For example, a player who has drawn or discarded a preparation card has
drawn or discarded one card, not two.
722.3. Preparation cards can’t be cast using the alternative characteristics found within their inset
frames. Rather, these characteristics are used to define characteristics of copies which may be cast.
722.3a Some spells and abilities cause a permanent with a prepare spell to become prepared or state
that a permanent enters prepared. If that permanent has the alternative characteristics of a
prepare spell, this gives the permanent the “prepared” designation. Prepared is a designation that
acts as a marker which rules and effects can identify. A permanent can’t gain this designation
unless it has a prepare spell, Additionally, a permanent can’t gain this designation if the
permanent already has it.
722.3b A rule or effect may cause a permanent to become “unprepared.” This removes the prepared
designation from that permanent.
722.3c As a permanent with a prepare spell gains the prepared designation or phases in prepared, its
controller creates a copy of that object in exile, except that copy has only the characteristics of
that permanent’s prepare spell, ignoring other exceptions to the copying process that apply to
that permanent. Those characteristics become the copy’s normal characteristics. This copy
remains in exile for as long as the prepared permanent remains on the battlefield and has the
prepared designation. This is an exception to rule 704.5e. For as long as the copy remains in
exile, the prepared permanent’s controller may cast the copy. That permanent loses the prepared
designation at the time the spell becomes cast (see rule 601.2i).
Example: A player casts Croaking Counterpart targeting an Encouraging Aviator.
Croaking Counterpart is an instant that says, in part, “Create a token that’s a copy of
target non-Frog creature, except it’s a 1/1 green Frog.” Encouraging Aviator is a
preparation creature card which says, in part, “Whenever this creature attacks, it
becomes prepared.” Its prepare spell is an instant named Jump which says “Target
creature gains flying until end of turn.
” The player then attacks with the token copy of
Encouraging Aviator. As the token copy of Encouraging Aviator becomes prepared, the
copy created in exile is a blue instant named Jump that says “Target creature gains
flying until end of turn.” It is not green, nor does it have power and toughness 1/1 or the
Frog creature type.
722.3d If a prepare spell is copied, the copy is also a prepare spell. Any rule or effect that refers to a
spell cast as a prepare spell refers to the copy as well.
722.4. In every zone, a preparation card has only its normal characteristics.
722.5. If an effect instructs a player to choose a card name and the player wants to choose a preparation
card’s alternative name, the player may do so.
*/

/**
 * @author TheElk801
 */
public abstract class PrepareCard extends CardWithSpellOption {

    private boolean prepareSpellCopy;

    protected PrepareCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String preparationName, CardType typeSpell, String costsSpell) {
        this(ownerId, setInfo, types, costs, preparationName, new CardType[]{typeSpell}, costsSpell);
    }

    protected PrepareCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String preparationName, CardType[] typesSpell, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new PrepareSpellCard(ownerId, setInfo, preparationName, typesSpell, costsSpell, this);
        this.addAbility(new PrepareReminderAbility());
    }

    protected PrepareCard(final PrepareCard card) {
        super(card);
        this.prepareSpellCopy = card.prepareSpellCopy;
    }

    public boolean isPrepareSpellCopy() {
        return prepareSpellCopy;
    }

    public void setPrepareSpellCopy(boolean prepareSpellCopy) {
        this.prepareSpellCopy = prepareSpellCopy;
    }

    @Override
    public String getName() {
        if (prepareSpellCopy && getSpellCard() != null) {
            return getSpellCard().getName();
        }
        return super.getName();
    }

    @Override
    public Abilities<Ability> getAbilities() {
        if (prepareSpellCopy) {
            return getSpellCard().getAbilities();
        }
        return getSharedAbilities(null);
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        // The alternative characteristics define the exile copy, never an ability of the
        // preparation card itself (including while that card is in a player's hand).
        if (prepareSpellCopy
                || game != null && game.getState().getValue("PreparePermanent" + getId()) != null) {
            return getSpellCard().getAbilities(game);
        }
        return getSharedAbilities(game);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (prepareSpellCopy && !ability.getSourceId().equals(getSpellCard().getId())) {
            return false;
        }
        if (ability.getSourceId().equals(getSpellCard().getId())) {
            UUID permanentId = (UUID) game.getState().getValue("PreparePermanent" + getId());
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                // Rule 722.3c: this happens as casting completes, irrespective of resolution.
                permanent.setPrepared(false, game);
            }
            return getSpellCard().cast(game, fromZone, ability, controllerId);
        }
        return super.cast(game, fromZone, ability, controllerId);
    }
}
