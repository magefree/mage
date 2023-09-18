package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.condition.common.ControlACommanderCondition;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KlauthsWill extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature without flying");

    static {
        filter.add(Predicates.not(new AbilityPredicate(FlyingAbility.class)));
    }

    public KlauthsWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{X}{R}{R}{G}");

        // Choose one. If you control a commander as you cast this spell, you may choose both.
        this.getSpellAbility().getModes().setChooseText(
                "Choose one. If you control a commander as you cast this spell, you may choose both."
        );
        this.getSpellAbility().getModes().setMoreCondition(ControlACommanderCondition.instance);

        // • Breathe Flame — Klauth's Will deals X damage to each creature without flying.
        this.getSpellAbility().addEffect(new DamageAllEffect(ManacostVariableValue.REGULAR, filter));
        this.getSpellAbility().withFirstModeFlavorWord("Breathe Flame");

        // • Smash Relics — Destroy up to X target artifacts and/or enchantments.
        this.getSpellAbility().addMode(new Mode(
                new DestroyTargetEffect().setText("destroy up to X target artifacts and/or enchantments")
        ).withFlavorWord("Smash Relics"));
        this.getSpellAbility().setTargetAdjuster(KlauthsWillAdjuster.instance);
    }

    private KlauthsWill(final KlauthsWill card) {
        super(card);
    }

    @Override
    public KlauthsWill copy() {
        return new KlauthsWill(this);
    }
}

enum KlauthsWillAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getEffects().stream().anyMatch(DestroyTargetEffect.class::isInstance)) {
            ability.getTargets().clear();
            ability.addTarget(new TargetPermanent(
                    0, ability.getManaCostsToPay().getX(),
                    StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT
            ));
        }
    }
}