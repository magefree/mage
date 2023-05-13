package mage.game.permanent.token;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HexproofAbility;
import mage.choices.ChoiceCreatureType;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class VolosJournalToken extends TokenImpl {

    public VolosJournalToken() {
        super("Volo's Journal", "Volo's Journal, a legendary colorless artifact token with hexproof and \"Whenever you cast a creature spell, note one of its creature types that hasn't been noted for this artifact.\"");
        this.supertype.add(SuperType.LEGENDARY);
        this.cardType.add(CardType.ARTIFACT);
        this.addAbility(HexproofAbility.getInstance());
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new VolosJournalTokenEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ).addHint(VolosJournalTokenHint.instance));
    }

    public VolosJournalToken(final VolosJournalToken token) {
        super(token);
    }

    public VolosJournalToken copy() {
        return new VolosJournalToken(this);
    }

    public static Set<String> getNotedTypes(Game game, Permanent permanent) {
        if (permanent == null) {
            return new HashSet<>();
        }

        String key = "notedTypes_" + permanent.getId() + '_' + permanent.getZoneChangeCounter(game);
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
        Permanent permanent = game.getPermanent(ability.getSourceId());
        Set<String> types = VolosJournalToken.getNotedTypes(game, permanent);
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
        Spell spell = (Spell) getValue("spellCast");
        if (spell == null) {
            return false;
        }

        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return true;
        }

        Permanent permanent = game.getPermanent(source.getSourceId());

        Set<String> types = VolosJournalToken.getNotedTypes(game, permanent);

        ChoiceCreatureType choice = new ChoiceCreatureType();

        // By default ChoiceCreatureType pre-populates all creatures into choices
        // Limit the available choices to those on the creature being cast
        if (!spell.isAllCreatureTypes(game)) {
            choice.setChoices(
                    spell.getSubtype(game)
                            .stream()
                            .filter(subType -> subType.getSubTypeSet() == SubTypeSet.CreatureType)
                            .map(SubType::getDescription)
                            .collect(Collectors.toSet())
            );
        }
        // Remove from the possible choices the subtypes which have already been chosen.
        choice.getChoices().removeIf(types::contains);

        switch (choice.getChoices().size()) {
            case 0:
                return false;
            case 1:
                types.add(choice.getChoices().stream().findFirst().get());
                return true;
        }

        player.choose(outcome, choice, game);
        types.add(choice.getChoice());
        return true;
    }
}
