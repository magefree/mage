
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.FlipSourceEffect;
import mage.abilities.effects.common.PreventAllDamageToAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreatureInPlay;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RuneTailKitsuneAscendant extends CardImpl {

    public RuneTailKitsuneAscendant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.flipCard = true;
        this.flipCardName = "Rune-Tail's Essence";

        // When you have 30 or more life, flip Rune-Tail, Kitsune Ascendant.
        this.addAbility(new RuneTailKitsuneAscendantFlipAbility());
    }

    public RuneTailKitsuneAscendant(final RuneTailKitsuneAscendant card) {
        super(card);
    }

    @Override
    public RuneTailKitsuneAscendant copy() {
        return new RuneTailKitsuneAscendant(this);
    }
}

class RuneTailKitsuneAscendantFlipAbility extends StateTriggeredAbility {

    public RuneTailKitsuneAscendantFlipAbility() {
        super(Zone.BATTLEFIELD, new FlipSourceEffect(new RuneTailEssence()));
    }

    public RuneTailKitsuneAscendantFlipAbility(final RuneTailKitsuneAscendantFlipAbility ability) {
        super(ability);
    }

    @Override
    public RuneTailKitsuneAscendantFlipAbility copy() {
        return new RuneTailKitsuneAscendantFlipAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null) {
            return controller.getLife() >= 30;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When you have 30 or more life, flip {this}.";
    }

}

class RuneTailEssence extends TokenImpl {

    RuneTailEssence() {
        super("Rune-Tail's Essence", "");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ENCHANTMENT);

        color.setWhite(true);

        // Prevent all damage that would be dealt to creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new PreventAllDamageToAllEffect(Duration.WhileOnBattlefield, new FilterControlledCreatureInPlay("creatures you control"))));
    }
    public RuneTailEssence(final RuneTailEssence token) {
        super(token);
    }

    public RuneTailEssence copy() {
        return new RuneTailEssence(this);
    }
}
