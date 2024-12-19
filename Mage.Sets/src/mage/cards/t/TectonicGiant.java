package mage.cards.t;

import mage.MageInt;
import mage.abilities.Mode;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.ExileTopXMayPlayUntilEffect;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TectonicGiant extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public TectonicGiant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.GIANT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Tectonic Giant attacks or becomes the target of a spell an opponent controls, choose one —
        // • Tectonic Giant deals 3 damage to each opponent.
        // • Exile the top two cards of your library. Choose one of them. Until the end of your next turn, you may play that card.
        TriggeredAbility ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD, new DamagePlayersEffect(3, TargetController.OPPONENT), false,
                "Whenever {this} attacks or becomes the target of a spell an opponent controls, ",
                new AttacksTriggeredAbility(null),
                new BecomesTargetSourceTriggeredAbility(null, filter)
        );
        ability.addMode(new Mode(new ExileTopXMayPlayUntilEffect(2, true, Duration.UntilEndOfYourNextTurn)));
        this.addAbility(ability);
    }

    private TectonicGiant(final TectonicGiant card) {
        super(card);
    }

    @Override
    public TectonicGiant copy() {
        return new TectonicGiant(this);
    }
}
