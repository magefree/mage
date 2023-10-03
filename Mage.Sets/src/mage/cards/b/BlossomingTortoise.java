package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
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
        ability.addEffect(new BlossomingTortoiseEffect());
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

class BlossomingTortoiseEffect extends OneShotEffect {

    BlossomingTortoiseEffect() {
        super(Outcome.Benefit);
        staticText = ", then return a land card from your graveyard to the battlefield tapped";
    }

    private BlossomingTortoiseEffect(final BlossomingTortoiseEffect effect) {
        super(effect);
    }

    @Override
    public BlossomingTortoiseEffect copy() {
        return new BlossomingTortoiseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null || player.getGraveyard().count(StaticFilters.FILTER_CARD_LAND, game) < 1) {
            return false;
        }
        TargetCard target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_LAND);
        target.withNotTarget(true);
        player.choose(outcome, target, source, game);
        Card card = game.getCard(target.getFirstTarget());
        return card != null && player.moveCards(card, Zone.BATTLEFIELD, source, game, true, false, false, null);
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
        if (!(abilityToModify instanceof ActivatedAbility)) {
            return false;
        }
        Permanent permanent = abilityToModify.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && permanent.isControlledBy(source.getControllerId())
                && permanent.isLand(game);
    }

}
