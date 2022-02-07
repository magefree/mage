package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.ZoneChangeTriggeredAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.common.FilterArtifactOrEnchantmentCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;
import mage.target.targetadjustment.TargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TameshiRealityArchitect extends CardImpl {

    public TameshiRealityArchitect(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.MOONFOLK);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever one or more noncreature permanents are returned to hand, draw a card. This ability triggers only once each turn.
        this.addAbility(new TameshiRealityArchitectTriggeredAbility());

        // {X}{W}, Return a land you control to its owner's hand: Return target artifact or enchantment card with mana value X or less from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect()
                        .setText("return target artifact or enchantment card with " +
                                "mana value X or less from your graveyard to the battlefield"),
                new ManaCostsImpl<>("{X}{W}")
        );
        ability.addCost(new ReturnToHandChosenControlledPermanentCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND)
        ));
        this.addAbility(ability.setTargetAdjuster(TameshiRealityArchitectAdjuster.instance));
    }

    private TameshiRealityArchitect(final TameshiRealityArchitect card) {
        super(card);
    }

    @Override
    public TameshiRealityArchitect copy() {
        return new TameshiRealityArchitect(this);
    }
}

enum TameshiRealityArchitectAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterCard filter = new FilterArtifactOrEnchantmentCard(
                "artifact or enchantment card with mana value " + xValue + " or less from your graveyard"
        );
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, xValue + 1));
        ability.getTargets().clear();
        ability.addTarget(new TargetCardInYourGraveyard(filter));
    }
}

class TameshiRealityArchitectTriggeredAbility extends ZoneChangeTriggeredAbility {

    TameshiRealityArchitectTriggeredAbility() {
        super(Zone.BATTLEFIELD, Zone.BATTLEFIELD, Zone.HAND, new DrawCardSourceControllerEffect(1),
                "Whenever one or more noncreature permanents are returned to hand, ", false);
        this.setTriggersOnce(true);
    }

    private TameshiRealityArchitectTriggeredAbility(final TameshiRealityArchitectTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TameshiRealityArchitectTriggeredAbility copy() {
        return new TameshiRealityArchitectTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && !((ZoneChangeEvent) event).getTarget().isCreature(game);
    }
}
