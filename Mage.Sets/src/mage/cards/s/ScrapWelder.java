package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.VariableCostImpl;
import mage.abilities.costs.VariableCostType;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.filter.common.FilterArtifactCard;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author weirddan455
 */
public final class ScrapWelder extends CardImpl {

    public ScrapWelder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {T}, Sacrifice an artifact with mana value X: Return target artifact card with mana value less than X from your graveyard to the battlefield. It gains haste until end of turn.
        Ability ability = new SimpleActivatedAbility(new ScrapWelderEffect(), new TapSourceCost());
        ability.addCost(new ScrapWelderCost());
        ability.setTargetAdjuster(ScrapWelderTargetAdjuster.instance);
        this.addAbility(ability);
    }

    private ScrapWelder(final ScrapWelder card) {
        super(card);
    }

    @Override
    public ScrapWelder copy() {
        return new ScrapWelder(this);
    }
}

class ScrapWelderEffect extends OneShotEffect {

    public ScrapWelderEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Return target artifact card with mana value less than X from your graveyard to the battlefield. It gains haste until end of turn";
    }

    private ScrapWelderEffect(final ScrapWelderEffect effect) {
        super(effect);
    }

    @Override
    public ScrapWelderEffect copy() {
        return new ScrapWelderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (controller == null || card == null || game.getState().getZone(card.getId()) != Zone.GRAVEYARD) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent != null) {
            ContinuousEffect effect = new GainAbilityTargetEffect(HasteAbility.getInstance());
            effect.setTargetPointer(new FixedTarget(permanent, game));
            game.addEffect(effect, source);
        }
        return true;
    }
}

class ScrapWelderCost extends VariableCostImpl {

    public ScrapWelderCost() {
        super(VariableCostType.NORMAL, "mana value");
        this.text = "Sacrifice an artifact with mana value X";
    }

    private ScrapWelderCost(final ScrapWelderCost cost) {
        super(cost);
    }

    @Override
    public ScrapWelderCost copy() {
        return new ScrapWelderCost(this);
    }

    @Override
    public Cost getFixedCostsFromAnnouncedValue(int xValue) {
        FilterControlledArtifactPermanent filter = new FilterControlledArtifactPermanent("an artifact with mana value " + xValue);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, xValue));
        return new SacrificeTargetCost(filter);
    }
}

enum ScrapWelderTargetAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = 0;
        for (Cost cost : ability.getCosts()) {
            if (cost instanceof ScrapWelderCost) {
                xValue = ((ScrapWelderCost) cost).getAmount();
                break;
            }
        }
        FilterArtifactCard filter = new FilterArtifactCard("artifact card with mana value less than " + xValue + " from your graveyard");
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}
