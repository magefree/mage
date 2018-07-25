package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksAloneTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedAllEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.CantBeBlockedSourceAbility;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author NinthWorld
 */
public final class DarkTemplar extends CardImpl {

    public DarkTemplar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        
        this.subtype.add(SubType.PROTOSS);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(1);

        // Dark Templar is unblockable as long as it's attacking alone.
        this.addAbility(new AttacksAloneTriggeredAbility(
                new GainAbilitySourceEffect(new CantBeBlockedSourceAbility(), Duration.EndOfCombat)
                        .setText("{this} is unblockable as long as it's attacking alone")));
    }

    public DarkTemplar(final DarkTemplar card) {
        super(card);
    }

    @Override
    public DarkTemplar copy() {
        return new DarkTemplar(this);
    }
}
