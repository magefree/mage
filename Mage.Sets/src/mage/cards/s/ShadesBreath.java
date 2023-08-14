package mage.cards.s;

import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadesBreath extends CardImpl {

    public ShadesBreath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Until end of turn, each creature you control becomes a black Shade and gains "{B}: This creature gets +1/+1 until end of turn."
        this.getSpellAbility().addEffect(new ShadesBreathSetColorEffect());
        this.getSpellAbility().addEffect(new ShadesBreathSetSubtypeEffect());
        this.getSpellAbility().addEffect(
                new GainAbilityControlledEffect(new SimpleActivatedAbility(
                        Zone.BATTLEFIELD,
                        new BoostSourceEffect(1, 1, Duration.EndOfTurn).setText("this creature gets +1/+1 until end of turn"),
                        new ManaCostsImpl<>("{B}")
                ), Duration.EndOfTurn, StaticFilters.FILTER_CONTROLLED_A_CREATURE)
                        .setText("and gains \"{B}: This creature gets +1/+1 until end of turn.\"")
        );
    }

    private ShadesBreath(final ShadesBreath card) {
        super(card);
    }

    @Override
    public ShadesBreath copy() {
        return new ShadesBreath(this);
    }
}

class ShadesBreathSetColorEffect extends ContinuousEffectImpl {

    public ShadesBreathSetColorEffect() {
        super(Duration.EndOfTurn, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "Until end of turn, each creature you control becomes a black";
    }

    public ShadesBreathSetColorEffect(final ShadesBreathSetColorEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game);
        for (Permanent permanent : permanents) {
            if (permanent != null) {
                permanent.getColor(game).setColor(ObjectColor.BLACK);
            }
        }
        return true;
    }

    @Override
    public ShadesBreathSetColorEffect copy() {
        return new ShadesBreathSetColorEffect(this);
    }
}

class ShadesBreathSetSubtypeEffect extends ContinuousEffectImpl {

    ShadesBreathSetSubtypeEffect() {
        super(Duration.EndOfTurn, Layer.TypeChangingEffects_4, SubLayer.NA, Outcome.Benefit);
        staticText = " Shade";
    }

    private ShadesBreathSetSubtypeEffect(final ShadesBreathSetSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getAllActivePermanents(
                StaticFilters.FILTER_PERMANENT_CREATURE, source.getControllerId(), game
        );
        for (Permanent permanent : permanents) {
            if (permanent == null) {
                continue;
            }
            permanent.removeAllCreatureTypes(game);
            permanent.addSubType(game, SubType.SHADE);
        }
        return true;
    }

    @Override
    public ShadesBreathSetSubtypeEffect copy() {
        return new ShadesBreathSetSubtypeEffect(this);
    }
}
