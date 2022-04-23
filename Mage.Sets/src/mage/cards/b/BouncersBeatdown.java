package mage.cards.b;

import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceTargetsPermanentCondition;
import mage.abilities.dynamicvalue.common.GreatestPowerAmongControlledCreaturesValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BouncersBeatdown extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("a black permanent");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    private static final Condition condition = new SourceTargetsPermanentCondition(filter);

    public BouncersBeatdown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // This spell costs {2} less to cast if it targets a black permanent.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SpellCostReductionSourceEffect(3, condition).setCanWorksOnStackOnly(true)
        ).setRuleAtTheTop(true));

        // Bouncer's Beatdown deals X damage to target creature or planeswalker, where X is the greatest power among creatures you control. If that creature or planeswalker would die this turn, exile it instead.
        this.getSpellAbility().addEffect(new DamageTargetEffect(GreatestPowerAmongControlledCreaturesValue.instance));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        this.getSpellAbility().addEffect(new ExileTargetIfDiesEffect("creature or planeswalker"));
        this.getSpellAbility().addHint(GreatestPowerAmongControlledCreaturesValue.getHint());
    }

    private BouncersBeatdown(final BouncersBeatdown card) {
        super(card);
    }

    @Override
    public BouncersBeatdown copy() {
        return new BouncersBeatdown(this);
    }
}
