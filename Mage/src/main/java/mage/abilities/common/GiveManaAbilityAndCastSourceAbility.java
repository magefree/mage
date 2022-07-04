package mage.abilities.common;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbilityImpl;
import mage.abilities.Mode;
import mage.abilities.costs.common.ExileSourceFromHandCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.mana.*;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.target.common.TargetLandPermanent;
import mage.util.CardUtil;
import mage.watchers.Watcher;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public class GiveManaAbilityAndCastSourceAbility extends ActivatedAbilityImpl {

    // TODO: write automated tests for this (it works in manual testing)
    public GiveManaAbilityAndCastSourceAbility(String colors) {
        super(Zone.HAND, new GainManaAbilitiesWhileExiledEffect(colors), new GenericManaCost(2));
        this.addCost(new ExileSourceFromHandCost());
        this.addEffect(new CastExiledFromHandCardEffect());
        this.addTarget(new TargetLandPermanent());
        this.addWatcher(new WasCastFromExileWatcher());
    }

    private GiveManaAbilityAndCastSourceAbility(final GiveManaAbilityAndCastSourceAbility ability) {
        super(ability);
    }

    @Override
    public GiveManaAbilityAndCastSourceAbility copy() {
        return new GiveManaAbilityAndCastSourceAbility(this);
    }
}

class CastExiledFromHandCardEffect extends OneShotEffect {

    CastExiledFromHandCardEffect() {
        super(Outcome.Benefit);
        staticText = "You may cast {this} for as long as it remains exiled";
    }

    private CastExiledFromHandCardEffect(final CastExiledFromHandCardEffect effect) {
        super(effect);
    }

    @Override
    public CastExiledFromHandCardEffect copy() {
        return new CastExiledFromHandCardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(getValue("exiledHandCardRef"))
                .filter(Objects::nonNull)
                .map(MageObjectReference.class::cast)
                .map(mor -> mor.getCard(game))
                .ifPresent(card -> CardUtil.makeCardPlayable(
                        game, source, card, Duration.Custom, false
                ));
        return true;
    }
}

class GainManaAbilitiesWhileExiledEffect extends ContinuousEffectImpl {

    private final String colors;

    GainManaAbilitiesWhileExiledEffect(String colors) {
        super(Duration.Custom, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.colors = colors;
    }

    private GainManaAbilitiesWhileExiledEffect(final GainManaAbilitiesWhileExiledEffect effect) {
        super(effect);
        this.colors = effect.colors;
    }

    @Override
    public GainManaAbilitiesWhileExiledEffect copy() {
        return new GainManaAbilitiesWhileExiledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (WasCastFromExileWatcher.check((MageObjectReference) getValue("exiledHandCardRef"), game)) {
            discard();
            return false;
        }
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        for (char c : colors.toCharArray()) {
            Ability ability;
            switch (c) {
                case 'W':
                    ability = new WhiteManaAbility();
                    break;
                case 'U':
                    ability = new BlueManaAbility();
                    break;
                case 'B':
                    ability = new BlackManaAbility();
                    break;
                case 'R':
                    ability = new RedManaAbility();
                    break;
                case 'G':
                    ability = new GreenManaAbility();
                    break;
                default:
                    continue;
            }
            permanent.addAbility(ability, source.getSourceId(), game);
        }
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return "target land gains \"{T}: Add " +
                CardUtil.concatWithOr(
                        Arrays.stream(colors.split(""))
                                .map(s -> '{' + s + '}')
                                .collect(Collectors.toList())
                ) +
                "\" until {this} is cast from exile";
    }
}

class WasCastFromExileWatcher extends Watcher {

    private final Set<MageObjectReference> morSet = new HashSet<>();

    WasCastFromExileWatcher() {
        super(WatcherScope.GAME);
    }

    @Override
    public void watch(GameEvent event, Game game) {
        if (event.getType() != GameEvent.EventType.SPELL_CAST || !Zone.EXILED.match(event.getZone())) {
            return;
        }
        Spell spell = game.getSpell(event.getSourceId());
        if (spell != null) {
            morSet.add(new MageObjectReference(spell.getMainCard(), game, -1));
        }
    }

    static boolean check(MageObjectReference mor, Game game) {
        return game
                .getState()
                .getWatcher(WasCastFromExileWatcher.class)
                .morSet
                .contains(mor);
    }
}
