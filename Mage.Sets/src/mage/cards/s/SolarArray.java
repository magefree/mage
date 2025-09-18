package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.delayed.CastNextSpellDelayedTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.keyword.SunburstAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

import java.util.UUID;

/**
 * @author TheElk801, PurpleCrowbar
 */
public final class SolarArray extends CardImpl {

    public SolarArray(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {T}: Add one mana of any color. When you next cast an artifact spell this turn, that spell gains sunburst.
        AnyColorManaAbility ability = new AnyColorManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(new CastNextSpellDelayedTriggeredAbility(
                new SolarArrayEffect(), StaticFilters.FILTER_SPELL_AN_ARTIFACT, true
        )));
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private SolarArray(final SolarArray card) {
        super(card);
    }

    @Override
    public SolarArray copy() {
        return new SolarArray(this);
    }
}

class SolarArrayEffect extends ContinuousEffectImpl {

    private UUID permanentId;
    private int zoneChangeCounter;

    SolarArrayEffect() {
        super(Duration.OneUse, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        staticText = "that spell gains sunburst";
    }

    private SolarArrayEffect(final SolarArrayEffect effect) {
        super(effect);
        this.permanentId = effect.permanentId;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }

    @Override
    public SolarArrayEffect copy() {
        return new SolarArrayEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        Spell object = game.getSpell(getTargetPointer().getFirst(game, source));
        if (object != null) {
            permanentId = object.getSourceId();
            zoneChangeCounter = game.getState().getZoneChangeCounter(object.getSourceId()) + 1;
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (game.getState().getZoneChangeCounter(permanentId) >= zoneChangeCounter) {
            discard();
            return false;
        }
        Permanent permanent = game.getPermanent(permanentId);
        if (permanent != null && permanent.getZoneChangeCounter(game) <= zoneChangeCounter) {
            permanent.addAbility(new SunburstAbility(permanent), source.getSourceId(), game);
            return true;
        }
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            game.getState().addOtherAbility(spell.getCard(), new SunburstAbility(spell), true);
        }
        return true;
    }
}
