package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BrimstoneTrebuchet extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.KNIGHT, "a Knight");

    public BrimstoneTrebuchet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {T}: Brimstone Trebuchet deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(
                new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost()
        ));

        // Whenever a Knight enters the battlefield under your control, untap Brimstone Trebuchet.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new UntapSourceEffect(), filter));
    }

    private BrimstoneTrebuchet(final BrimstoneTrebuchet card) {
        super(card);
    }

    @Override
    public BrimstoneTrebuchet copy() {
        return new BrimstoneTrebuchet(this);
    }
}
