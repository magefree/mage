
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox
 */
public final class Stratadon extends CardImpl {

    public Stratadon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{10}");
        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Domain - Stratadon costs {1} less to cast for each basic land type among lands you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new StratadonCostReductionEffect()).addHint(DomainHint.instance));
        // Trample
        this.addAbility(TrampleAbility.getInstance());
    }

    private Stratadon(final Stratadon card) {
        super(card);
    }

    @Override
    public Stratadon copy() {
        return new Stratadon(this);
    }
}

class StratadonCostReductionEffect extends CostModificationEffectImpl {

    public StratadonCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "<i>Domain</i> &mdash; This spell costs {1} less to cast for each basic land type among lands you control.";
    }

    protected StratadonCostReductionEffect(final StratadonCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, DomainValue.REGULAR.calculate(game, source, this));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public StratadonCostReductionEffect copy() {
        return new StratadonCostReductionEffect(this);
    }
}
