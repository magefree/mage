package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WolverineRiders extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ELF, "another Elf");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public WolverineRiders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of each upkeep, create a 1/1 green Elf Warrior creature token.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new ElfWarriorToken()), TargetController.EACH_PLAYER, false
        ));

        // Whenever another Elf enters the battlefield under your control, you gain life equal to its toughness.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                new GainLifeEffect(WolverineRidersValue.instance, "you gain life equal to its toughness"), filter
        ));
    }

    private WolverineRiders(final WolverineRiders card) {
        super(card);
    }

    @Override
    public WolverineRiders copy() {
        return new WolverineRiders(this);
    }
}

enum WolverineRidersValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent permanent = (Permanent) effect.getValue("permanentEnteringBattlefield");
        return permanent == null ? 0 : permanent.getToughness().getValue();
    }

    @Override
    public WolverineRidersValue copy() {
        return instance;
    }

    @Override
    public String getMessage() {
        return "";
    }
}
