package mage.cards.n;

import java.util.UUID;

import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.AddCardSubtypeAllEffect;
import mage.abilities.effects.common.continuous.BecomesColorAllEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author noahg
 */
public final class Nightcreep extends CardImpl {

    public Nightcreep(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{B}{B}");
        

        // Until end of turn, all creatures become black and all lands become Swamps.
        this.getSpellAbility().addEffect(new BecomesColorAllEffect(ObjectColor.BLACK, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, true, "Until end of turn, all creatures become black and"));
        this.getSpellAbility().addEffect(new NightcreepEffect());
    }

    public Nightcreep(final Nightcreep card) {
        super(card);
    }

    @Override
    public Nightcreep copy() {
        return new Nightcreep(this);
    }
}

class NightcreepEffect extends ContinuousEffectImpl {

    public NightcreepEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
    }

    public NightcreepEffect(NightcreepEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        for (Permanent land : game.getBattlefield().getActivePermanents(StaticFilters.FILTER_LAND, source.getControllerId(), game)) {
            switch (layer) {
                case TypeChangingEffects_4:
                    // 305.7 Note that this doesn't remove any abilities that were granted to the land by other effects
                    // So the ability removing has to be done before Layer 6
                    land.removeAllAbilities(source.getSourceId(), game);
                    land.getSubtype(game).removeAll(SubType.getLandTypes(false));
                    land.getSubtype(game).add(SubType.SWAMP);
                    break;
                case AbilityAddingRemovingEffects_6:
                    land.addAbility(new BlackManaAbility(), source.getSourceId(), game);
                    break;
            }
        }
        return true;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.AbilityAddingRemovingEffects_6 || layer == Layer.TypeChangingEffects_4;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public NightcreepEffect copy() {
        return new NightcreepEffect(this);
    }
}