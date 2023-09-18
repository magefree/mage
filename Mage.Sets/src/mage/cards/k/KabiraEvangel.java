package mage.cards.k;

import mage.MageInt;
import mage.abilities.common.AllyEntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.GainProtectionFromColorAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class KabiraEvangel extends CardImpl {

    private static final FilterPermanent FILTER1 = new FilterControlledPermanent(SubType.ALLY, "Ally");

    public KabiraEvangel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever Kabira Evangel or another Ally enters the battlefield under your control, you may choose a color. If you do, Allies you control gain protection from the chosen color until end of turn.
        this.addAbility(new AllyEntersBattlefieldTriggeredAbility(
                new GainProtectionFromColorAllEffect(Duration.EndOfTurn, FILTER1)
                        .setText("choose a color. If you do, Allies you control gain protection " +
                                "from the chosen color until end of turn."),
                true
        ).setAbilityWord(null));
    }

    private KabiraEvangel(final KabiraEvangel card) {
        super(card);
    }

    @Override
    public KabiraEvangel copy() {
        return new KabiraEvangel(this);
    }
}
