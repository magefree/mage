
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.BushidoAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class BrothersYamazaki extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
        filter.add(new NamePredicate("Brothers Yamazaki"));
    }

    public BrothersYamazaki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SAMURAI);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Bushido 1
        this.addAbility(new BushidoAbility(1));

        // If there are exactly two permanents named Brothers Yamazaki on the battlefield, the "legend rule" doesn't apply to them.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BrothersYamazakiIgnoreLegendRuleEffectEffect()));

        // Each other creature named Brothers Yamazaki gets +2/+2 and has haste.
        Effect effect = new BoostAllEffect(2, 2, Duration.WhileOnBattlefield, filter, true);
        effect.setText("Each other creature named Brothers Yamazaki gets +2/+2");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        effect = new GainAbilityAllEffect(HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter);
        effect.setText("and has haste");
        ability.addEffect(effect);
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

class BrothersYamazakiIgnoreLegendRuleEffectEffect extends ContinuousRuleModifyingEffectImpl {

    private static final FilterPermanent filter = new FilterPermanent();

    static {
        filter.add(new NamePredicate("Brothers Yamazaki"));
    }

    public BrothersYamazakiIgnoreLegendRuleEffectEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
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
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT_BY_LEGENDARY_RULE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null && permanent.getName().equals("Brothers Yamazaki")) {
            return game.getBattlefield().count(filter, source.getControllerId(), source, game) == 2;
        }
        return false;
    }

}
