package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CadricSoulKindler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another nontoken legendary permanent");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(SuperType.LEGENDARY.getPredicate());
    }

    public CadricSoulKindler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // The "legend rule" doesn't apply to tokens you control.
        this.addAbility(new SimpleStaticAbility(new CadricSoulKindlerLegendEffect()));

        // Whenever another nontoken legendary permanent enters the battlefield under your control, you may pay {1}. If you do, create a token that's a copy of it. That token gains haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DoIfCostPaid(new CadricSoulKindlerLegendEffect(), new GenericManaCost(1)), filter
        ));
    }

    private CadricSoulKindler(final CadricSoulKindler card) {
        super(card);
    }

    @Override
    public CadricSoulKindler copy() {
        return new CadricSoulKindler(this);
    }
}

class CadricSoulKindlerLegendEffect extends ContinuousRuleModifyingEffectImpl {

    public CadricSoulKindlerLegendEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, false, false);
        staticText = "the \"legend rule\" doesn't apply to tokens you control";
    }

    public CadricSoulKindlerLegendEffect(final CadricSoulKindlerLegendEffect effect) {
        super(effect);
    }

    @Override
    public CadricSoulKindlerLegendEffect copy() {
        return new CadricSoulKindlerLegendEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT_BY_LEGENDARY_RULE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        return permanent instanceof PermanentToken && permanent.isControlledBy(source.getControllerId());
    }

}

class CadricSoulKindlerEffect extends OneShotEffect {

    CadricSoulKindlerEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of it. " +
                "That token gains haste. Sacrifice it at the beginning of the next end step";
    }

    private CadricSoulKindlerEffect(final CadricSoulKindlerEffect effect) {
        super(effect);
    }

    @Override
    public CadricSoulKindlerEffect copy() {
        return new CadricSoulKindlerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect();
        effect.setSavedPermanent(permanent);
        effect.apply(game, source);
        game.addEffect(new GainAbilityTargetEffect(
                HasteAbility.getInstance(), Duration.Custom
        ).setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game)), source);
        effect.sacrificeTokensCreatedAtNextEndStep(game, source);
        return true;
    }
}
