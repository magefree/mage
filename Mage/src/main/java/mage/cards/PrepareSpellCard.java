package mage.cards;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility.ActivationStatus;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.ZoneChangeInfo;
import mage.game.ZonesHandler;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class PrepareSpellCard extends CardImpl implements SpellOptionCard {

    private PrepareCard prepareCardParent;

    public PrepareSpellCard(UUID ownerId, CardSetInfo setInfo, String preparationName,
                            CardType[] cardTypes, String costs, PrepareCard prepareCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.PREPARE_SPELL);

        PrepareSpellAbility newSpellAbility = new PrepareSpellAbility(getSpellAbility(), preparationName, cardTypes, costs);
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
    public PrepareSpellCard copy() {
        return new PrepareSpellCard(this);
    }

    @Override
    public SpellAbility getSpellAbility() {
        SpellAbility ability = super.getSpellAbility();
        if (ability != null) {
            return ability;
        }
        for (Ability cardAbility : abilities) {
            if (cardAbility instanceof SpellAbility
                    && ((SpellAbility) cardAbility).getSpellAbilityType() != SpellAbilityType.BASE_ALTERNATE) {
                return spellAbility = (SpellAbility) cardAbility;
            }
        }
        return null;
    }

    @Override
    public UUID getOwnerId() {
        return prepareCardParent.getOwnerId();
    }

    @Override
    public String getExpansionSetCode() {
        return prepareCardParent.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return prepareCardParent.getCardNumber();
    }

    @Override
    public boolean getUsesVariousArt() {
        return prepareCardParent.getUsesVariousArt();
    }

    @Override
    public String getImageFileName() {
        return prepareCardParent.getImageFileName().isEmpty()
                ? prepareCardParent.getName()
                : prepareCardParent.getImageFileName();
    }

    @Override
    public Integer getImageNumber() {
        return prepareCardParent.getImageNumber();
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        if (!PrepareUtil.canCastPreparedSpell(controllerId, this, game)) {
            return false;
        }

        ZoneChangeEvent event = new ZoneChangeEvent(this.getId(), ability, controllerId, fromZone, Zone.STACK);
        mage.game.stack.Spell spell = new mage.game.stack.Spell(
                this,
                ability.getSpellAbilityToResolve(game),
                controllerId,
                event.getFromZone(),
                game
        );
        if (ZonesHandler.cast(new ZoneChangeInfo.Stack(event, spell), ability, game)) {
            PrepareUtil.onPrepareSpellCast(this, controllerId, game);
            return true;
        }
        return false;
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        Zone fromZone = game.getState().getZone(getId());
        if (toZone == Zone.GRAVEYARD && fromZone == Zone.STACK) {
            mage.game.stack.Spell spell = source == null ? null : game.getStack().getSpell(source.getId());
            if (spell == null) {
                spell = game.getStack().getSpell(getId());
            }
            if (spell != null) {
                game.getStack().remove(spell, game);
            }
            game.setZone(getId(), Zone.OUTSIDE);
            return true;
        }
        boolean moved = super.moveToZone(toZone, source, game, flag, appliedEffects);
        if (moved && fromZone == Zone.EXILED && toZone != Zone.STACK) {
            PrepareUtil.onPrepareSpellRemovedFromExile(this, game);
        }
        return moved;
    }

    @Override
    public PrepareCard getMainCard() {
        return prepareCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(getId(), zone);
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
    public void finalizeSpell() {
        if (spellAbility instanceof PrepareSpellAbility) {
            ((PrepareSpellAbility) spellAbility).finalizePrepare();
        }
    }

    @Override
    public String getIdName() {
        return getName() + " [" + prepareCardParent.getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getSpellType() {
        return "Prepare";
    }

    public String getTypeLineForView(Game game) {
        StringBuilder sbType = new StringBuilder("Prepare ");
        for (SuperType superType : getSuperType(game)) {
            sbType.append(superType).append(' ');
        }
        for (CardType cardType : getCardType(game)) {
            sbType.append(cardType.toString()).append(' ');
        }
        if (!getSubtype(game).isEmpty()) {
            sbType.append("- ");
            for (SubType subType : getSubtype(game)) {
                sbType.append(subType).append(' ');
            }
        }
        return sbType.toString();
    }

    public String getRulePrefixForParentView(Game game) {
        StringBuilder sb = new StringBuilder(getTypeLineForView(game).trim())
                .append(" &mdash; ")
                .append(getName());
        String costs = getManaCost().getText();
        if (!costs.isEmpty()) {
            sb.append(' ').append(costs);
        }
        return sb.append(" &mdash; ").toString();
    }
}

class PrepareSpellAbility extends SpellAbility {

    private String nameFull;
    private boolean finalized = false;

    PrepareSpellAbility(final SpellAbility baseSpellAbility, String preparationName,
                        CardType[] cardTypes, String costs) {
        super(baseSpellAbility);
        this.setName(cardTypes, preparationName, costs);
        this.setCardName(preparationName);
        this.zone = Zone.EXILED;
        this.spellAbilityType = SpellAbilityType.PREPARE_SPELL;
    }

    private PrepareSpellAbility(final PrepareSpellAbility ability) {
        super(ability);
        this.nameFull = ability.nameFull;
        this.finalized = ability.finalized;
    }

    public void finalizePrepare() {
        if (finalized) {
            throw new IllegalStateException("Wrong code usage. Prepare (" + cardName + ") "
                    + "needs to call finalizePrepare() exactly once.");
        }
        this.finalized = true;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        Card sourceCard = game.getCard(getSourceId());
        if (!(sourceCard instanceof PrepareSpellCard)) {
            return ActivationStatus.getFalse();
        }
        if (!PrepareUtil.canCastPreparedSpell(playerId, (PrepareSpellCard) sourceCard, game)) {
            return ActivationStatus.getFalse();
        }
        if (spellCanBeActivatedNow(playerId, game).isEmpty()) {
            return ActivationStatus.getFalse();
        }
        if (game.inCheckPlayableState()) {
            GameEvent castEvent = GameEvent.getEvent(GameEvent.EventType.CAST_SPELL, this.getId(), this, playerId);
            castEvent.setZone(game.getState().getZone(sourceCard.getId()));
            if (game.getContinuousEffects().preventedByRuleModification(castEvent, this, game, true)) {
                return ActivationStatus.getFalse();
            }
        }
        return ActivationStatus.withoutApprovingObject(
                getCosts().canPay(this, this, playerId, game)
                        && canChooseTarget(game, playerId)
        );
    }

    public void setName(CardType[] cardTypes, String name, String costs) {
        this.nameFull = "Prepare "
                + Arrays.stream(cardTypes)
                .map(CardType::toString)
                .collect(Collectors.joining(" "))
                + " &mdash; "
                + name;

        this.name = this.nameFull + " " + costs;
    }

    @Override
    public String getRule(boolean all) {
        return CardUtil.getTextWithFirstCharUpperCase(super.getRule(false));
    }

    @Override
    public PrepareSpellAbility copy() {
        return new PrepareSpellAbility(this);
    }
}
