package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.AbilitiesImpl;
import mage.abilities.Ability;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public abstract class PrepareCard extends CardWithSpellOption {

    protected PrepareCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String preparationName, CardType typeSpell, String costsSpell) {
        this(ownerId, setInfo, types, costs, preparationName, new CardType[]{typeSpell}, costsSpell);
    }

    protected PrepareCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costs, String preparationName, CardType[] typesSpell, String costsSpell) {
        super(ownerId, setInfo, types, costs);
        this.spellCard = new PrepareSpellCard(ownerId, setInfo, preparationName, typesSpell, costsSpell, this);
    }

    protected PrepareCard(final PrepareCard card) {
        super(card);
    }

    @Override
    public SpellOptionCard getSpellCard() {
        return spellCard;
    }

    public List<String> getRulesForMainCardView(Card displayedCard, Game game) {
        List<String> rules = new ArrayList<>(displayedCard.getRules(game));
        PrepareSpellCard prepareSpellCard = (PrepareSpellCard) spellCard;
        List<String> prepareSpellRules = prepareSpellCard.getRules(game);
        prepareSpellRules.forEach(rules::remove);
        prepareSpellRules.forEach(rule -> rules.add(prepareSpellCard.getRulePrefixForParentView(game) + rule));
        return rules;
    }

    public List<String> getPrepareRulesForView(Game game) {
        return spellCard.getRules(game);
    }

    public String getPrepareTypeLineForView(Game game) {
        return ((PrepareSpellCard) spellCard).getTypeLineForView(game);
    }

    @Override
    public Abilities<Ability> getAbilities() {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();
        allAbilities.addAll(super.getSharedAbilities(null));
        allAbilities.addAll(spellCard.getAbilities());
        return allAbilities;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        Abilities<Ability> allAbilities = new AbilitiesImpl<>();
        allAbilities.addAll(super.getSharedAbilities(game));
        allAbilities.addAll(spellCard.getAbilities(game));
        return allAbilities;
    }

    @Override
    public boolean cast(Game game, Zone fromZone, mage.abilities.SpellAbility ability, UUID controllerId) {
        if (ability.getSpellAbilityType() == SpellAbilityType.PREPARE_SPELL) {
            return this.getSpellCard().cast(game, fromZone, ability, controllerId);
        }
        this.getSpellCard().getSpellAbility().setControllerId(controllerId);
        return super.cast(game, fromZone, ability, controllerId);
    }
}
