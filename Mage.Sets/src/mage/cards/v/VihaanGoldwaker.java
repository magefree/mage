package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureAllEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.OutlawPredicate;
import mage.game.permanent.token.custom.CreatureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class VihaanGoldwaker extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("outlaws");

    static {
        filter.add(OutlawPredicate.instance);
    }

    private static final FilterPermanent filterTreasures =
            new FilterControlledPermanent(SubType.TREASURE, "Treasures you control");

    public VihaanGoldwaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Other outlaws you control have vigilance and haste.
        Ability ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                VigilanceAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                HasteAbility.getInstance(), Duration.WhileOnBattlefield, filter, true
        ).setText("and haste"));
        this.addAbility(ability);

        // At the beginning of combat on your turn, you may have Treasures you control become 3/3 Construct Assassin artifact creatures in addition to their other types until end of turn.
        this.addAbility(new BeginningOfCombatTriggeredAbility(
                new BecomesCreatureAllEffect(
                        new CreatureToken(3, 3).withType(CardType.ARTIFACT)
                                .withSubType(SubType.CONSTRUCT).withSubType(SubType.ASSASSIN),
                        "", filterTreasures, Duration.EndOfTurn, false
                ).setText("have Treasures you control become 3/3 Construct Assassin artifact creatures "
                        + "in addition to their other types until end of turn"),
                TargetController.YOU,
                true
        ));
    }

    private VihaanGoldwaker(final VihaanGoldwaker card) {
        super(card);
    }

    @Override
    public VihaanGoldwaker copy() {
        return new VihaanGoldwaker(this);
    }
}
