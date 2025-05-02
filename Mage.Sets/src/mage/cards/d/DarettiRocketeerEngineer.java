package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarettiRocketeerEngineer extends CardImpl {

    public DarettiRocketeerEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Daretti's power is equal to the greatest mana value among artifacts you control.
        this.addAbility(new SimpleStaticAbility(new SetBasePowerSourceEffect(DarettiRocketeerEngineerValue.instance)
                .setText("{this}'s power is equal to the greatest mana value among artifacts you control")));

        // Whenever Daretti enters or attacks, choose target artifact card in your graveyard. You may sacrifice an artifact. If you do, return the chosen card to the battlefield.
        Ability ability = new EntersBattlefieldOrAttacksSourceTriggeredAbility(new DoIfCostPaid(
                new ReturnFromGraveyardToBattlefieldTargetEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT)
        ).setText("choose target artifact card in your graveyard. You may sacrifice an artifact. If you do, return the chosen card to the battlefield"));
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private DarettiRocketeerEngineer(final DarettiRocketeerEngineer card) {
        super(card);
    }

    @Override
    public DarettiRocketeerEngineer copy() {
        return new DarettiRocketeerEngineer(this);
    }
}

enum DarettiRocketeerEngineerValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        return game
                .getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_PERMANENT_ARTIFACT,
                        sourceAbility.getControllerId(), sourceAbility, game
                )
                .stream()
                .mapToInt(MageObject::getManaValue)
                .max()
                .orElse(0);
    }

    @Override
    public DarettiRocketeerEngineerValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "";
    }

    @Override
    public String toString() {
        return "1";
    }
}
