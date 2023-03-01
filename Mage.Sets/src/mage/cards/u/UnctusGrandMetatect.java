package mage.cards.u;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnctusGrandMetatect extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("blue creatures");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public UnctusGrandMetatect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.VEDALKEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other blue creatures you control have "Whenever this creature becomes tapped, draw a card, then discard a card."
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledEffect(
                new BecomesTappedSourceTriggeredAbility(
                        new DrawDiscardControllerEffect(1, 1)
                ), Duration.WhileOnBattlefield, filter, true
        )));

        // Other artifact creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE, true
        )));

        // {U/P}: Until end of turn, target creature you control becomes a blue artifact in addition to its other colors and types. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new UnctusGrandMetatectEffect(), new ManaCostsImpl<>("{U/P}")
        );
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private UnctusGrandMetatect(final UnctusGrandMetatect card) {
        super(card);
    }

    @Override
    public UnctusGrandMetatect copy() {
        return new UnctusGrandMetatect(this);
    }
}

class UnctusGrandMetatectEffect extends ContinuousEffectImpl {

    UnctusGrandMetatectEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "until end of turn, target creature you control " +
                "becomes a blue artifact in addition to its other colors and types";
    }

    private UnctusGrandMetatectEffect(final UnctusGrandMetatectEffect effect) {
        super(effect);
    }

    @Override
    public UnctusGrandMetatectEffect copy() {
        return new UnctusGrandMetatectEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            discard();
            return false;
        }
        switch (layer) {
            case TypeChangingEffects_4:
                permanent.addCardType(game, CardType.ARTIFACT);
                return true;
            case ColorChangingEffects_5:
                permanent.getColor(game).setBlue(true);
                return true;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        switch (layer) {
            case TypeChangingEffects_4:
            case ColorChangingEffects_5:
                return true;
        }
        return false;
    }
}
