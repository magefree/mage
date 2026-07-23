package mage.cards;

import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.TimingRule;
import mage.abilities.Ability;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;

import java.util.UUID;

/*
The inset frame of a preparation card that includes its alternative characteristics. See rule 722, “Preparation Cards.”
*/

/**
 * @author TheElk801
 */
public class PrepareSpellCard extends CardImpl implements SpellOptionCard {

    private PrepareCard prepareCardParent;

    public PrepareSpellCard(UUID ownerId, CardSetInfo setInfo, String preparationName, CardType[] cardTypes, String costs, PrepareCard prepareCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.BASE_ALTERNATE);

        SpellAbility newSpellAbility = new PrepareCardSpellAbility(
                new ManaCostsImpl<>(costs), preparationName, Zone.HAND,
                SpellAbilityType.BASE_ALTERNATE, prepareCardParent.getId()
        );
        if (this.isSorcery()) {
            newSpellAbility.setTiming(TimingRule.SORCERY);
        }
        // CardImpl created a default spell using the preparation card's main name.
        // replaceSpellAbility does not find BASE_ALTERNATE abilities, so using it here
        // left that default "Cast <permanent>" spell alongside the prepare spell.
        abilities.clear();
        this.addAbility(newSpellAbility);
        spellAbility = newSpellAbility;

        this.setName(preparationName);
        this.prepareCardParent = prepareCardParent;
    }

    protected PrepareSpellCard(final PrepareSpellCard card) {
        super(card);
        this.prepareCardParent = card.prepareCardParent;
        // CardImpl intentionally clears its cached spell ability during copying,
        // and its getter excludes BASE_ALTERNATE abilities. Restore the cache from
        // the copied ability list so both object graphs remain aligned.
        for (Ability ability : abilities) {
            if (ability instanceof SpellAbility) {
                this.spellAbility = (SpellAbility) ability;
                break;
            }
        }
    }

    @Override
    public PrepareSpellCard copy() {
        return new PrepareSpellCard(this);
    }

    @Override
    public void finalizeSpell() {
        // Prepare spells need no post-construction ability changes.
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        UUID permanentId = (UUID) game.getState().getValue("PreparePermanent" + getMainCard().getId());
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null) {
            // Rule 722.3c: casting the linked copy consumes the designation even if
            // the resulting spell is countered or otherwise fails to resolve.
            permanent.setPrepared(false, game);
        }
        return super.cast(game, fromZone, ability, controllerId);
    }

    @Override
    public PrepareCard getMainCard() {
        return prepareCardParent;
    }

    @Override
    public void setParentCard(CardWithSpellOption card) {
        this.prepareCardParent = (PrepareCard) card;
    }

    @Override
    public PrepareCard getParentCard() {
        return prepareCardParent;
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return prepareCardParent.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return prepareCardParent.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public void setZone(Zone zone, Game game) {
        prepareCardParent.setZone(zone, game);
    }

    @Override
    public String getSpellType() {
        return "Prepare";
    }
}

class PrepareCardSpellAbility extends SpellAbility {

    private final UUID parentCardId;

    PrepareCardSpellAbility(ManaCostsImpl<ManaCost> costs, String name, Zone zone,
                            SpellAbilityType spellAbilityType, UUID parentCardId) {
        super(costs, name, zone, spellAbilityType);
        this.parentCardId = parentCardId;
    }

    private PrepareCardSpellAbility(final PrepareCardSpellAbility ability) {
        super(ability);
        this.parentCardId = ability.parentCardId;
    }

    @Override
    public PrepareCardSpellAbility copy() {
        return new PrepareCardSpellAbility(this);
    }

    @Override
    public Card getCharacteristics(Game game) {
        Card parent = game.getCard(parentCardId);
        return parent instanceof PrepareCard
                ? ((PrepareCard) parent).getSpellCard().copy()
                : null;
    }
}
