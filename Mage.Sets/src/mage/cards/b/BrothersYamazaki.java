package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class BrothersYamazaki extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new NamePredicate("Brothers Yamazaki"));
    }

    public BrothersYamazaki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));

        // If there are exactly two permanents named Brothers Yamazaki on the battlefield, the "legend rule" doesn't apply to them.
        this.addAbility(new SimpleStaticAbility(new BrothersYamazakiIgnoreLegendRuleEffectEffect()));

        // Each other creature named Brothers Yamazaki gets +2/+2 and has haste.
        Ability ability = new SimpleStaticAbility(new BoostAllEffect(
                2, 2, Duration.WhileOnBattlefield, filter, true
        ).setText("Each other creature named Brothers Yamazaki gets +2/+2"));
        ability.addEffect(new GainAbilityAllEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and has haste"));
        this.addAbility(ability);
    }

    private BrothersYamazaki(final BrothersYamazaki card) {
        super(card);
    }

    @Override
    public BrothersYamazaki copy() {
        return new BrothersYamazaki(this);
    }
}

class BrothersYamazakiIgnoreLegendRuleEffectEffect extends ContinuousEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new NamePredicate("Brothers Yamazaki"));
    }

    public BrothersYamazakiIgnoreLegendRuleEffectEffect() {
        super(Duration.WhileOnBattlefield, Layer.RulesEffects, SubLayer.NA, Outcome.Detriment);
        staticText = "If there are exactly two permanents named Brothers Yamazaki on the battlefield, the \"legend rule\" doesn't apply to them";
    }

    public BrothersYamazakiIgnoreLegendRuleEffectEffect(final BrothersYamazakiIgnoreLegendRuleEffectEffect effect) {
        super(effect);
    }

    @Override
    public BrothersYamazakiIgnoreLegendRuleEffectEffect copy() {
        return new BrothersYamazakiIgnoreLegendRuleEffectEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(
                filter, source.getControllerId(), source, game
        );
        if (permanents.size() != 2) {
            return false;
        }
        for (Permanent permanent : permanents) {
            permanent.setLegendRuleApplies(false);
        }
        return true;
    }
}
