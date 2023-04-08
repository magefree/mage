package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.MillCardsEachPlayerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlittingGuerrilla extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature or battle card from your graveyard");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
    }

    public FlittingGuerrilla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Flitting Guerrilla dies, each player mills two cards. Then you may exile Flitting Guerrilla. When you do, put target creature or battle card from your graveyard on top of your library.
        ReflexiveTriggeredAbility rAbility = new ReflexiveTriggeredAbility(new PutOnLibraryTargetEffect(true), false);
        rAbility.addTarget(new TargetCardInYourGraveyard(filter));
        Ability ability = new DiesSourceTriggeredAbility(new MillCardsEachPlayerEffect(2, TargetController.EACH_PLAYER));
        ability.addEffect(new DoWhenCostPaid(rAbility, new ExileSourceFromGraveCost(), "Exile this from your graveyard?"));
        this.addAbility(ability);
    }

    private FlittingGuerrilla(final FlittingGuerrilla card) {
        super(card);
    }

    @Override
    public FlittingGuerrilla copy() {
        return new FlittingGuerrilla(this);
    }
}
