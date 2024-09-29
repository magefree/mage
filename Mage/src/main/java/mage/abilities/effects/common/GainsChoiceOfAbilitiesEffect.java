package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTargets;
import mage.util.CardUtil;

import java.util.*;

/**
 * Something gains one ability of the player's choice from a list of abilities until end of turn.
 * Affects any targets set and optionally the source as well. Have no targets to affect only the source.
 *
 * @author notgreat, TheElk801
 */
public class GainsChoiceOfAbilitiesEffect extends OneShotEffect {

    public enum TargetType {
        Source, Target, Both
    }

    private final Map<String, Ability> abilityMap;
    private final boolean includeEnd;
    private final TargetType affects;
    private final String targetDescription;

    public GainsChoiceOfAbilitiesEffect(Ability... abilities) {
        this(TargetType.Target, null, true, abilities);
    }

    public GainsChoiceOfAbilitiesEffect(TargetType affects, Ability... abilities) {
        this(affects, null, true, abilities);
    }

    public GainsChoiceOfAbilitiesEffect(TargetType affects, String targetDescription, boolean includeEnd, Ability... abilities) {
        super(Outcome.AddAbility);
        this.affects = affects;
        this.targetDescription = targetDescription;
        this.includeEnd = includeEnd;
        this.abilityMap = new LinkedHashMap<>();
        for (Ability ability : abilities) {
            this.abilityMap.put(CardUtil.stripReminderText(ability.getRule()), ability);
        }
    }

    protected GainsChoiceOfAbilitiesEffect(final GainsChoiceOfAbilitiesEffect effect) {
        super(effect);
        this.affects = effect.affects;
        this.abilityMap = CardUtil.deepCopyObject(effect.abilityMap);
        this.targetDescription = effect.targetDescription;
        this.includeEnd = effect.includeEnd;
    }

    @Override
    public GainsChoiceOfAbilitiesEffect copy() {
        return new GainsChoiceOfAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        List<Permanent> permanents = new ArrayList<>();
        if (affects == TargetType.Source || affects == TargetType.Both) {
            permanents.add(source.getSourcePermanentIfItStillExists(game));
        }
        if (affects == TargetType.Target || affects == TargetType.Both) {
            for (UUID p : getTargetPointer().getTargets(game, source)) {
                permanents.add(game.getPermanent(p));
            }
        }
        permanents.removeIf(Objects::isNull);
        if (player == null || permanents.isEmpty()) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability to gain");
        choice.setChoices(new HashSet<>(abilityMap.keySet()));
        player.choose(outcome, choice, game);
        Ability ability = abilityMap.get(choice.getChoice());
        if (ability != null) {
            game.addEffect(new GainAbilityTargetEffect(ability, Duration.EndOfTurn)
                    .setTargetPointer(new FixedTargets(permanents, game)), source);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder();
        if (targetDescription != null) {
            if (targetDescription.isEmpty()){
                sb.append("gains");
            } else {
                sb.append(targetDescription);
                sb.append(" gains");
            }
        } else switch (affects) {
            case Source:
                sb.append("{this} gains");
                break;
            case Target:
                sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that creature"));
                sb.append(" gains");
                break;
            case Both:
                sb.append("{this} and ");
                sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that creature"));
                sb.append(" both gain");
                break;
        }
        sb.append(" your choice of ");
        String[] abilitiesText = abilityMap.keySet().toArray(new String[0]);
        if (abilityMap.size() == 2) {
            sb.append(abilitiesText[0]).append(" or ").append(abilitiesText[1]);
        } else if (abilityMap.size() > 2) {
            for (int i = 0; i < abilityMap.size() - 1; i += 1) {
                sb.append(abilitiesText[i]).append(", ");
            }
            sb.append("or ").append(abilitiesText[abilityMap.size()-1]);
        } else {
            throw new IllegalStateException("Not enough abilities for GainsChoiceOfAbilitiesEffect");
        }
        if (includeEnd) {
            sb.append(" until end of turn");
        }
        return sb.toString();
    }
}
