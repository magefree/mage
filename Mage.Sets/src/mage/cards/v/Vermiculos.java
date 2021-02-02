
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactPermanent;

/**
 *
 * @author fireshoes
 */
public final class Vermiculos extends CardImpl {

    public Vermiculos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an artifact enters the battlefield, Vermiculos gets +4/+4 until end of turn.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new BoostSourceEffect(4, 4, Duration.EndOfTurn),
                new FilterArtifactPermanent("an artifact"), false));
    }

    private Vermiculos(final Vermiculos card) {
        super(card);
    }

    @Override
    public Vermiculos copy() {
        return new Vermiculos(this);
    }
}
