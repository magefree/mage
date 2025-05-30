package mage.cards;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ShuffleIntoLibrarySourceEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class OmenSpellCard extends CardImpl implements SpellOptionCard {

    private OmenCard omenCardParent;

    public OmenSpellCard(UUID ownerId, CardSetInfo setInfo, String omenName, CardType[] cardTypes, String costs, OmenCard omenCard) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.OMEN_SPELL);
        this.subtype.add(SubType.OMEN);

        OmenCardSpellAbility newSpellAbility = new OmenCardSpellAbility(getSpellAbility(), omenName, cardTypes, costs);
        this.replaceSpellAbility(newSpellAbility);
        spellAbility = newSpellAbility;

        this.setName(omenName);
        this.omenCardParent = omenCard;
    }

    public void finalizeSpell() {
        if (spellAbility instanceof OmenCardSpellAbility) {
            ((OmenCardSpellAbility) spellAbility).finalizeOmen();
        }
    }

    protected OmenSpellCard(final OmenSpellCard card) {
        super(card);
        this.omenCardParent = card.omenCardParent;
    }

    @Override
    public UUID getOwnerId() {
        return omenCardParent.getOwnerId();
    }

    @Override
    public String getExpansionSetCode() {
        return omenCardParent.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return omenCardParent.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return omenCardParent.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return omenCardParent.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public OmenCard getMainCard() {
        return omenCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(omenCardParent.getId(), zone);
        game.setZone(omenCardParent.getSpellCard().getId(), zone);
    }

    @Override
    public OmenSpellCard copy() {
        return new OmenSpellCard(this);
    }

    @Override
    public void setParentCard(CardWithSpellOption card) {
        this.omenCardParent = (OmenCard) card;
    }

    @Override
    public OmenCard getParentCard() {
        return this.omenCardParent;
    }

    @Override
    public String getIdName() {
        // id must send to main card (popup card hint in game logs)
        return getName() + " [" + omenCardParent.getId().toString().substring(0, 3) + ']';
    }

    @Override
    public String getSpellType() {
        return "Omen";
    }
}

class OmenCardSpellAbility extends SpellAbility {

    private String nameFull;
    private boolean finalized = false;

    public OmenCardSpellAbility(final SpellAbility baseSpellAbility, String omenName, CardType[] cardTypes, String costs) {
        super(baseSpellAbility);
        this.setName(cardTypes, omenName, costs);
        this.setCardName(omenName);
    }

    public void finalizeOmen() {
        if (finalized) {
            throw new IllegalStateException("Wrong code usage. "
                    + "Omen (" + cardName + ") "
                    + "need to call finalizeOmen() exactly once.");
        }
        Effect effect = new ShuffleIntoLibrarySourceEffect();
        effect.setText("");
        this.addEffect(effect);
        this.finalized = true;
    }

    protected OmenCardSpellAbility(final OmenCardSpellAbility ability) {
        super(ability);
        this.nameFull = ability.nameFull;
        if (!ability.finalized) {
            throw new IllegalStateException("Wrong code usage. "
                    + "Omen (" + cardName + ") "
                    + "need to call finalizeOmen() at the very end of the card's constructor.");
        }
        this.finalized = true;
    }

    public void setName(CardType[] cardTypes, String omenName, String costs) {
        this.nameFull = "Omen " + Arrays.stream(cardTypes).map(CardType::toString).collect(Collectors.joining(" ")) + " &mdash; " + omenName;
        this.name = this.nameFull + " " + costs;
    }

    @Override
    public String getRule(boolean all) {
        // TODO: must hide rules in permanent like SpellAbility, but can't due effects text
        return this.nameFull
                + " "
                + getManaCosts().getText()
                + " &mdash; "
                + CardUtil.getTextWithFirstCharUpperCase(super.getRule(false)) // without cost
                + " <i>(Then shuffle this card into its owner's library.)</i>";
    }

    @Override
    public OmenCardSpellAbility copy() {
        return new OmenCardSpellAbility(this);
    }
}
