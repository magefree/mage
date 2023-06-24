package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BecomesSubtypeAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.DevoidAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.EldraziSliverToken;

import java.util.Arrays;
import java.util.UUID;

/**
 * @author Grath
 */
public final class SlivdraziMonstrosity extends CardImpl {

    private static final FilterCreaturePermanent eldrazi_you_control = new FilterCreaturePermanent("Eldrazi you control");

    static {
        eldrazi_you_control.add(SubType.ELDRAZI.getPredicate());
    }

    public SlivdraziMonstrosity(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{C}{W}{U}{B}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SLIVER);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Eldrazi you control are Slivers in addition to their other types.
        this.addAbility(new SimpleStaticAbility(new BecomesSubtypeAllEffect(
                Duration.WhileOnBattlefield, Arrays.asList(SubType.SLIVER), eldrazi_you_control, false
        ).setText("Eldrazi you control are Slivers in addition to their other types.")));

        // Slivers you control have devoid and annihilator 1.
        Ability ability = new SimpleStaticAbility(new SlivdraziMonstrosityEffect());
        ability.addEffect(
                new GainAbilityControlledEffect(
                new AnnihilatorAbility(1), Duration.WhileOnBattlefield,
                StaticFilters.FILTER_PERMANENT_SLIVERS
        ).setText("and annihilator 1"));
        this.addAbility(ability);

        // {3}: Create a 1/1 colorless Eldrazi Sliver creature token. It has “Sacrifice this creature: Add {C}.”
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreateTokenEffect(new EldraziSliverToken()), new ManaCostsImpl<>("{3}")));
    }

    private SlivdraziMonstrosity(final SlivdraziMonstrosity card) {
        super(card);
    }

    @Override
    public SlivdraziMonstrosity copy() {
        return new SlivdraziMonstrosity(this);
    }
}

class SlivdraziMonstrosityEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.SLIVER, "Slivers");

    public SlivdraziMonstrosityEffect() {
        super(Duration.WhileOnBattlefield, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        staticText = "Slivers you control have devoid";
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent perm : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source, game)) {
            if (perm.isControlledBy(source.getControllerId())) {
                // Due to the carefully calibrated system of layers that Magic uses to determine the interaction of continuous
                // effects, gaining an ability that changes the color of an object has no effect on the color of that object.
                // Despite this, Slivdrazi Monstrosity causes Slivers to become colorless as they gain devoid. Rather than try
                // to figure out how this could work, this card should just be played as though it read “Slivers you control
                // have devoid and annihilator 1 and are colorless.”
                // (2019-11-12)
                // This ruling implemented here by adding this ability in the color-changing effect layer.
                perm.addAbility(new DevoidAbility(perm.getColor(game)), source.getSourceId(), game);
            }
        }
        return true;
    }

    @Override
    public SlivdraziMonstrosityEffect copy() {
        return new SlivdraziMonstrosityEffect(this);
    }

    private SlivdraziMonstrosityEffect(final SlivdraziMonstrosityEffect effect) {
        super(effect);
    }
}