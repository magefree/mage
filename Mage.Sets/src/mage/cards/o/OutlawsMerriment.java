package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.OutlawsMerrimentClericToken;
import mage.game.permanent.token.OutlawsMerrimentRogueToken;
import mage.game.permanent.token.OutlawsMerrimentWarriorToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OutlawsMerriment extends CardImpl {

    public OutlawsMerriment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{W}{W}");

        // At the beginning of your upkeep, choose one at random. Create a red and white creature token with those characteristics.
        // • 3/1 Human Warrior with trample and haste.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new OutlawsMerrimentWarriorToken())
                        .setText("3/1 Human Warrior with trample and haste"),
                TargetController.YOU, false
        );

        // • 2/1 Human Cleric with lifelink and haste.
        ability.addMode(new Mode(new CreateTokenEffect(new OutlawsMerrimentClericToken())
                .setText("2/1 Human Cleric with lifelink and haste")));

        // • 1/2 Human Rogue with haste and "When this creature enters the battlefield, it deals 1 damage to any target."
        ability.addMode(new Mode(new CreateTokenEffect(new OutlawsMerrimentRogueToken())
                .setText("1/2 Human Rogue with haste and \"When this creature enters the battlefield, it deals 1 damage to any target.\"")));

        ability.getModes().setChooseText("choose one at random. Create a red and white creature token with those characteristics.");
        ability.getModes().setRandom(true);

        this.addAbility(ability);
    }

    private OutlawsMerriment(final OutlawsMerriment card) {
        super(card);
    }

    @Override
    public OutlawsMerriment copy() {
        return new OutlawsMerriment(this);
    }
}
