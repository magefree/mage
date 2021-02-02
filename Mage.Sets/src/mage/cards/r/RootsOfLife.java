package mage.cards.r;

import java.util.UUID;
import mage.abilities.common.BecomesTappedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

/**
 *
 * @author fubs
 */
public final class RootsOfLife extends CardImpl {

    private static final String ruleTrigger1 = "&bull Island &mdash; Whenever an Island an opponent controls becomes tapped, you gain 1 life";
    private static final String ruleTrigger2 = "&bull Swamp &mdash; Whenever a Swamp an opponent controls becomes tapped, you gain 1 life";

    private static final FilterPermanent islandFilter = new FilterPermanent("an Island an opponent controls");
    private static final FilterPermanent swampFilter = new FilterPermanent("a Swamp an opponent controls");

    static {
        islandFilter.add(SubType.ISLAND.getPredicate());
        islandFilter.add(TargetController.OPPONENT.getControllerPredicate());
        swampFilter.add(SubType.SWAMP.getPredicate());
        swampFilter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public RootsOfLife(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // As Roots of Life enters the battlefield, choose Island or Swamp.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Island or Swamp?", "Island", "Swamp"), null,
                "As {this} enters the battlefield, choose Island or Swamp.", ""));

        // Whenever a land of the chosen type an opponent controls becomes tapped, you gain 1 life.
        // * Island chosen
        this.addAbility(new ConditionalTriggeredAbility(
                new BecomesTappedTriggeredAbility(new GainLifeEffect(1), false, islandFilter),
                new ModeChoiceSourceCondition("Island"),
                ruleTrigger1));

        // * Swamp chosen
        this.addAbility(new ConditionalTriggeredAbility(
                new BecomesTappedTriggeredAbility(new GainLifeEffect(1), false, swampFilter),
                new ModeChoiceSourceCondition("Swamp"),
                ruleTrigger2));
    }

    private RootsOfLife(final RootsOfLife card) {
        super(card);
    }

    @Override
    public RootsOfLife copy() {
        return new RootsOfLife(this);
    }
}
