package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.RemoveCounterCost;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.permanent.CounterAnyPredicate;
import mage.target.TargetPermanent;

/**
 *
 * @author LevelX2
 */
public final class ChiseiHeartOfOceans extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPermanent("remove a counter from a permanent you control");

    static {
        filter.add(TargetController.YOU.getControllerPredicate());
        filter.add(CounterAnyPredicate.instance);
    }

    public ChiseiHeartOfOceans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your upkeep, sacrifice Chisei, Heart of Oceans unless you remove a counter from a permanent you control.
        TargetPermanent target = new TargetPermanent(1, 1, filter, true);
        target.setTargetName("a permanent you control");
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceUnlessPaysEffect(new RemoveCounterCost(target)), TargetController.YOU, false));

    }

    private ChiseiHeartOfOceans(final ChiseiHeartOfOceans card) {
        super(card);
    }

    @Override
    public ChiseiHeartOfOceans copy() {
        return new ChiseiHeartOfOceans(this);
    }
}
