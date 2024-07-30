
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

import java.util.*;

/**
 * Something gains one ability of the player's choice from a list of abilities until end of turn.
 * Affects any targets set and optionally the source as well. Have no targets to affect only the source.
 *
 * @author notgreat, TheElk801
 */
public class GainsChoiceOfAbilitiesEffect extends OneShotEffect {

    private final Map<String, Ability> abilityMap = new LinkedHashMap<>();
    private final boolean affectSource;
    private final String targetDescription;

    public GainsChoiceOfAbilitiesEffect(boolean affectSource, Ability... abilities) {
        this(affectSource, null, abilities);
    }
    public GainsChoiceOfAbilitiesEffect(boolean affectSource, String targetDescription, Ability... abilities) {
        super(Outcome.AddAbility);
        this.affectSource = affectSource;
        this.targetDescription = targetDescription;
        for (Ability ability : abilities) {
            this.abilityMap.put(ability.getRule(), ability);
        }
    }

    protected GainsChoiceOfAbilitiesEffect(final GainsChoiceOfAbilitiesEffect effect) {
        super(effect);
        this.affectSource = effect.affectSource;
        this.abilityMap.putAll(effect.abilityMap);
        this.targetDescription = effect.targetDescription;
    }

    @Override
    public GainsChoiceOfAbilitiesEffect copy() {
        return new GainsChoiceOfAbilitiesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        List<Permanent> permanents = new ArrayList<>();
        permanents.add(source.getSourcePermanentIfItStillExists(game));
        for (UUID p : getTargetPointer().getTargets(game, source)) {
            permanents.add(game.getPermanent(p));
        }
        permanents.removeIf(Objects::isNull);
        if (player == null || permanents.isEmpty()) {
            return false;
        }
        Choice choice = new ChoiceImpl(true);
        choice.setMessage("Choose an ability");
        choice.setChoices(new HashSet<>(abilityMap.keySet()));
        player.choose(outcome, choice, game);
        Ability ability = abilityMap.getOrDefault(choice.getChoice(), null);
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
        if (targetDescription != null){
            sb.append(targetDescription);
        } else if (affectSource){
            sb.append("{this}");
        } else {
            sb.append(getTargetPointer().describeTargets(mode.getTargets(), "that creature"));
        }
        sb.append(" gains your choice of ");
        String[] abilitiesText = abilityMap.keySet().toArray(new String[0]);
        if (abilityMap.size() == 2) {
            sb.append(abilitiesText[0]).append(" or ").append(abilitiesText[1]);
        } else if (abilityMap.size() > 2) {
            for (int i = 0; i < abilityMap.size() - 1; i += 1) {
                sb.append(abilitiesText[i]).append(", ");
            }
            sb.append("or ").append(abilitiesText[abilityMap.size()-1]);
        } else {
            throw new IllegalStateException("Only one ability found in GainsChoiceOfAbilitiesEffect");
        }

        sb.append(" until end of turn");
        return sb.toString();
    }
}
