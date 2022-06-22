package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HexproofAbility;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.RandomUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolosJournalToken extends TokenImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public VolosJournalToken() {
        super("Volo's Journal", "Volo's Journal, a legendary colorless artifact token with hexproof and \"Whenever you cast a creature spell, note one of its creature types that hasn't been noted for this artifact.\"");
        addSuperType(SuperType.LEGENDARY);
        this.cardType.add(CardType.ARTIFACT);
        this.addAbility(HexproofAbility.getInstance());
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new VolosJournalTokenEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).addHint(VolosJournalTokenHint.instance));

        availableImageSetCodes = Arrays.asList("CLB");
    }

    public VolosJournalToken(final VolosJournalToken token) {
        super(token);
    }

    public VolosJournalToken copy() {
        return new VolosJournalToken(this);
    }

    public static Set<String> getNotedTypes(Game game, Ability source) {
        return getNotedTypes(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());
    }

    public static Set<String> getNotedTypes(Game game, UUID sourceId, int zcc) {
        String key = "notedTypes_" + sourceId + '_' + zcc;
        Object value = game.getState().getValue(key);
        if (value == null) {
            Set<String> types = new HashSet<>();
            game.getState().setValue(key, types);
            return types;
        }
        return (Set<String>) value;
    }
}

enum VolosJournalTokenHint implements Hint {
    instance;

    @Override
    public String getText(Game game, Ability ability) {
        Set<String> types = VolosJournalToken.getNotedTypes(game, ability);
        int size = types.size();
        if (size > 0) {
            return "Creature types noted: " + size + " (" + String.join(", ", types) + ')';
        }
        return "No creature types currently noted";
    }

    @Override
    public VolosJournalTokenHint copy() {
        return this;
    }
}

class VolosJournalTokenEffect extends OneShotEffect {

    VolosJournalTokenEffect() {
        super(Outcome.Benefit);
        staticText = "note one of its creature types that hasn't been noted for this artifact";
    }

    private VolosJournalTokenEffect(final VolosJournalTokenEffect effect) {
        super(effect);
    }

    @Override
    public VolosJournalTokenEffect copy() {
        return new VolosJournalTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<String> types = VolosJournalToken.getNotedTypes(game, source);
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }
        ChoiceCreatureType choice = new ChoiceCreatureType();
        choice.getChoices().removeIf(types::contains);
        if (!spell.isAllCreatureTypes(game)) {
            spell.getSubtype(game)
                    .stream()
                    .filter(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType)
                    .map(SubType::getDescription)
                    .forEach(s -> choice.getChoices().removeIf(s::equals));
        }
        switch (choice.getChoices().size()) {
            case 0:
                return false;
            case 1:
                types.add(RandomUtil.randomFromCollection(choice.getChoices()));
                return true;
        }
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.choose(outcome, choice, game);
        types.add(choice.getChoice());
        return true;
    }
}
