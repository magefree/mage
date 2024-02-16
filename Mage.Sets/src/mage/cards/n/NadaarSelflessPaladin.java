package mage.cards.n;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.keyword.VentureIntoTheDungeonEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NadaarSelflessPaladin extends CardImpl {

    public NadaarSelflessPaladin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Nadaar, Selfless Paladin enters the battlefield or attacks, venture into the dungeon.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new VentureIntoTheDungeonEffect()));

        // Other creatures you control get +1/+1 as long as you've completed a dungeon.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new BoostControlledEffect(
                        1, 1, Duration.WhileOnBattlefield, true
                ), CompletedDungeonCondition.instance,
                "other creatures you control get +1/+1 as long as you've completed a dungeon"
        )).addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private NadaarSelflessPaladin(final NadaarSelflessPaladin card) {
        super(card);
    }

    @Override
    public NadaarSelflessPaladin copy() {
        return new NadaarSelflessPaladin(this);
    }
}
