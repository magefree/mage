package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BlossomingTortoise extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("land creatures");

    static {
        filter.add(CardType.LAND.getPredicate());
    }

    public BlossomingTortoise(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.TURTLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Blossoming Tortoise enters the battlefield or attacks, mill three cards, then return a land card from your graveyard to the battlefield tapped.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(false,
                StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD, PutCards.BATTLEFIELD_TAPPED).concatBy(", then"));
        this.addAbility(ability);

        // Activated abilities of lands you control cost {1} less to activate.
        this.addAbility(new SimpleStaticAbility(new BlossomingTortoiseCostReductionEffect()));

        // Land creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(
                new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filter)
        ));
    }

    private BlossomingTortoise(final BlossomingTortoise card) {
        super(card);
    }

    @Override
    public BlossomingTortoise copy() {
        return new BlossomingTortoise(this);
    }
}

class BlossomingTortoiseCostReductionEffect extends CostModificationEffectImpl {

    BlossomingTortoiseCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = "activated abilities of lands you control cost {1} less to activate";
    }

    private BlossomingTortoiseCostReductionEffect(BlossomingTortoiseCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public BlossomingTortoiseCostReductionEffect copy() {
        return new BlossomingTortoiseCostReductionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.reduceCost(abilityToModify, 1);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!abilityToModify.isActivatedAbility()) {
            return false;
        }
        Permanent permanent = abilityToModify.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isLand(game);
    }

}
