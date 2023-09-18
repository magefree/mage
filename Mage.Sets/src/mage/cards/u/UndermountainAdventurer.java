package mage.cards.u;

import mage.MageInt;
import mage.Mana;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.CompletedDungeonCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalManaEffect;
import mage.abilities.effects.common.TakeTheInitiativeEffect;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.watchers.common.CompletedDungeonWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UndermountainAdventurer extends CardImpl {

    public UndermountainAdventurer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // When Undermountain Adventurer enters the battlefield, you take the initiative.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TakeTheInitiativeEffect()));

        // {T}: Add {G}{G}. If you've completed a dungeon, add six {G} instead.
        this.addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD,
                new ConditionalManaEffect(
                        new BasicManaEffect(Mana.GreenMana(6)),
                        new BasicManaEffect(Mana.GreenMana(2)),
                        CompletedDungeonCondition.instance, "Add {G}{G}. " +
                        "If you've completed a dungeon, add six {G} instead."
                ), new TapSourceCost()
        ).addHint(CompletedDungeonCondition.getHint()), new CompletedDungeonWatcher());
    }

    private UndermountainAdventurer(final UndermountainAdventurer card) {
        super(card);
    }

    @Override
    public UndermountainAdventurer copy() {
        return new UndermountainAdventurer(this);
    }
}
