package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.permanent.token.KnightToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheCircleOfLoyalty extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a legendary spell");

    static {
        filter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }

    public TheCircleOfLoyalty(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}{W}{W}");

        this.addSuperType(SuperType.LEGENDARY);

        // This spell costs {1} less to cast for each Knight you control.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new TheCircleOfLoyaltyCostReductionEffect()));

        // Creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield)
        ));

        // Whenever you cast a legendary spell, create a 2/2 white Knight creature token with vigilance.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new KnightToken()), filter, false
        ));

        // {3}{W}, {T}: Create a 2/2 white Knight creature token with vigilance.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new KnightToken()), new ManaCostsImpl("{3}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private TheCircleOfLoyalty(final TheCircleOfLoyalty card) {
        super(card);
    }

    @Override
    public TheCircleOfLoyalty copy() {
        return new TheCircleOfLoyalty(this);
    }
}

class TheCircleOfLoyaltyCostReductionEffect extends CostModificationEffectImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.KNIGHT);

    TheCircleOfLoyaltyCostReductionEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "This spell costs {1} less to cast for each Knight you control";
    }

    private TheCircleOfLoyaltyCostReductionEffect(final TheCircleOfLoyaltyCostReductionEffect effect) {
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
        return abilityToModify instanceof SpellAbility
                && abilityToModify.getSourceId().equals(source.getSourceId())
                && game.getCard(abilityToModify.getSourceId()) != null;
    }

    @Override
    public TheCircleOfLoyaltyCostReductionEffect copy() {
        return new TheCircleOfLoyaltyCostReductionEffect(this);
    }
}
