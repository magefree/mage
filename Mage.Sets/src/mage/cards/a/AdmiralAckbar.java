
package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.RebelStarshipToken;

import java.util.UUID;

/**
 *
 * @author Styxo
 */
public final class AdmiralAckbar extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Starship creatures");

    static {
        filter.add(SubType.STARSHIP.getPredicate());
    }

    public AdmiralAckbar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.CALAMARI);
        this.subtype.add(SubType.REBEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When you cast Admiral Ackbar, create two 2/3 blue Rebel Starship artifact creature tokens with spaceflight named B-Wing.
        this.addAbility(new CastSourceTriggeredAbility(new CreateTokenEffect(new RebelStarshipToken(), 2), false));

        // At the beginning of each upkeep, untap all starships you control.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(TargetController.ANY, new UntapAllControllerEffect(filter), false));

        // Whenever two or more Starship creatures you control attack, draw a card.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 2, filter));
    }

    private AdmiralAckbar(final AdmiralAckbar card) {
        super(card);
    }

    @Override
    public AdmiralAckbar copy() {
        return new AdmiralAckbar(this);
    }
}
