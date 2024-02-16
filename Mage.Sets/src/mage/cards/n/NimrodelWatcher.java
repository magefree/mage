package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ScryTriggeredAbility;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NimrodelWatcher extends CardImpl {

    public NimrodelWatcher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you scry, Nimrodel Watcher gets +1/+0 until end of turn and can't be blocked this turn. This ability triggers only once each turn.
        Ability ability = new ScryTriggeredAbility(new BoostSourceEffect(
                1, 0, Duration.EndOfTurn
        )).setTriggersOnceEachTurn(true);
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn)
                .setText("and can't be blocked this turn"));
        this.addAbility(ability);
    }

    private NimrodelWatcher(final NimrodelWatcher card) {
        super(card);
    }

    @Override
    public NimrodelWatcher copy() {
        return new NimrodelWatcher(this);
    }
}
