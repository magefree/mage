package mage.cards;

import mage.abilities.Ability;
import mage.abilities.Modes;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.ExileAdventureSpellEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author phulin
 */
public class AdventureCardSpellImpl extends CardImpl implements AdventureCardSpell {

    private AdventureCard adventureCardParent;

    public AdventureCardSpellImpl(UUID ownerId, CardSetInfo setInfo, String adventureName, CardType[] cardTypes, String costs, AdventureCard adventureCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.ADVENTURE_SPELL);
        this.subtype.add(SubType.ADVENTURE);

        AdventureCardSpellAbility newSpellAbility = new AdventureCardSpellAbility(getSpellAbility(), adventureName, cardTypes, costs);
        this.replaceSpellAbility(newSpellAbility);
        spellAbility = newSpellAbility;

        this.setName(adventureName);
        this.adventureCardParent = adventureCardParent;
    }

    public AdventureCardSpellImpl(final AdventureCardSpellImpl card) {
        super(card);
        this.adventureCardParent = card.adventureCardParent;
    }

    @Override
    public UUID getOwnerId() {
        return adventureCardParent.getOwnerId();
    }

    @Override
    public String getExpansionSetCode() {
        return adventureCardParent.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return adventureCardParent.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, Ability source, Game game, boolean flag, List<UUID> appliedEffects) {
        return adventureCardParent.moveToZone(toZone, source, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, Ability source, Game game, List<UUID> appliedEffects) {
        return adventureCardParent.moveToExile(exileId, name, source, game, appliedEffects);
    }

    @Override
    public AdventureCard getMainCard() {
        return adventureCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(adventureCardParent.getId(), zone);
        game.setZone(adventureCardParent.getSpellCard().getId(), zone);
    }

    @Override
    public AdventureCardSpell copy() {
        return new AdventureCardSpellImpl(this);
    }

    @Override
    public void setParentCard(AdventureCard card) {
        this.adventureCardParent = card;
    }

    @Override
    public AdventureCard getParentCard() {
        return this.adventureCardParent;
    }

    @Override
    public String getIdName() {
        // id must send to main card (popup card hint in game logs)
        return getName() + " [" + adventureCardParent.getId().toString().substring(0, 3) + ']';
    }
}

class AdventureCardSpellAbility extends SpellAbility {

    String nameFull;

    public AdventureCardSpellAbility(final SpellAbility baseSpellAbility, String adventureName, CardType[] cardTypes, String costs) {
        super(baseSpellAbility);
        this.setName(cardTypes, adventureName, costs);
        this.addEffect(ExileAdventureSpellEffect.getInstance());
        this.setCardName(adventureName);
    }

    public AdventureCardSpellAbility(final AdventureCardSpellAbility ability) {
        super(ability);
        this.nameFull = ability.nameFull;
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        ExileZone adventureExileZone = game.getExile().getExileZone(ExileAdventureSpellEffect.adventureExileId(playerId, game));
        Card spellCard = game.getCard(this.getSourceId());
        if (spellCard instanceof AdventureCardSpell) {
            Card card = ((AdventureCardSpell) spellCard).getParentCard();
            if (adventureExileZone != null && adventureExileZone.contains(card.getId())) {
                return ActivationStatus.getFalse();
            }
        }
        return super.canActivate(playerId, game);
    }

    public void setName(CardType[] cardTypes, String name, String costs) {
        this.nameFull = "Adventure " + Arrays.stream(cardTypes).map(CardType::toString).collect(Collectors.joining(" ")) + " &mdash; " + name;
        this.name = this.nameFull + " " + costs;
    }

    @Override
    public String getRule(boolean all) {
        return this.getRule();
    }

    @Override
    public String getRule() {
        StringBuilder sbRule = new StringBuilder();
        sbRule.append(this.nameFull);
        sbRule.append(" ");
        sbRule.append(manaCosts.getText());
        sbRule.append(" &mdash; ");
        Modes modes = this.getModes();
        if (modes.size() <= 1) {
            sbRule.append(modes.getMode().getEffects().getTextStartingUpperCase(modes.getMode()));
        } else {
            sbRule.append(getModes().getText());
        }
        sbRule.append(" <i>(Then exile this card. You may cast the creature later from exile.)</i>");
        return sbRule.toString();
    }

    @Override
    public SpellAbility copy() {
        return new AdventureCardSpellAbility(this);
    }
}