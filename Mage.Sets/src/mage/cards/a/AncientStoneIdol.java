package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.SubType;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AttackingPredicate;
import mage.game.Game;
import mage.game.permanent.token.StoneTrapIdolToken;
import mage.util.CardUtil;

/**
 *
 * @author TheElk801
 */
public final class AncientStoneIdol extends CardImpl {

    public AncientStoneIdol(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{10}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(12);
        this.toughness = new MageInt(12);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // This spell costs {1} less to cast for each attacking creature.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new AncientStoneIdolCostReductionEffect()));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Ancient Stone Idol dies, create a 6/12 colorless Construct artifact creature token with trample.
        this.addAbility(new DiesTriggeredAbility(new CreateTokenEffect(new StoneTrapIdolToken())));
    }

    public AncientStoneIdol(final AncientStoneIdol card) {
        super(card);
    }

    @Override
    public AncientStoneIdol copy() {
        return new AncientStoneIdol(this);
    }
}

class AncientStoneIdolCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(new AttackingPredicate());
    }

    public AncientStoneIdolCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each attacking creature";
    }

    protected AncientStoneIdolCostReductionEffect(AncientStoneIdolCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        CardUtil.reduceCost(abilityToModify, reductionAmount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if ((abilityToModify instanceof SpellAbility) && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return game.getCard(abilityToModify.getSourceId()) != null;
        }
        return false;
    }

    @Override
    public AncientStoneIdolCostReductionEffect copy() {
        return new AncientStoneIdolCostReductionEffect(this);
    }
}
