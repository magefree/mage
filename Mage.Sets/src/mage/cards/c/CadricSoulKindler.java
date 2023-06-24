package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.ruleModifying.LegendRuleDoesntApplyEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CadricSoulKindler extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent("another nontoken legendary permanent");
    private static final FilterPermanent filter2
            = new FilterControlledPermanent("tokens you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
        filter.add(SuperType.LEGENDARY.getPredicate());
        filter2.add(TokenPredicate.TRUE);
    }

    public CadricSoulKindler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // The "legend rule" doesn't apply to tokens you control.
        this.addAbility(new SimpleStaticAbility(new LegendRuleDoesntApplyEffect(filter2)));

        // Whenever another nontoken legendary permanent enters the battlefield under your control, you may pay {1}. If you do, create a token that's a copy of it. That token gains haste. Sacrifice it at the beginning of the next end step.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new DoIfCostPaid(new CadricSoulKindlerTokenEffect(), new GenericManaCost(1)), filter
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

class CadricSoulKindlerTokenEffect extends OneShotEffect {

    CadricSoulKindlerTokenEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of it. " +
                "That token gains haste. Sacrifice it at the beginning of the next end step";
    }

    private CadricSoulKindlerTokenEffect(final CadricSoulKindlerTokenEffect effect) {
        super(effect);
    }

    @Override
    public CadricSoulKindlerTokenEffect copy() {
        return new CadricSoulKindlerTokenEffect(this);
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
