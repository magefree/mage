package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeldedMoxite extends CardImpl {

    public MeldedMoxite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        // When this artifact enters, you may discard a card. If you do, draw two cards.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new DoIfCostPaid(new DrawCardSourceControllerEffect(2), new DiscardCardCost())
        ));

        // {3}, Sacrifice this artifact: Create a tapped 2/2 colorless Robot artifact creature token.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new RobotToken(), 1, true), new GenericManaCost(3)
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private MeldedMoxite(final MeldedMoxite card) {
        super(card);
    }

    @Override
    public MeldedMoxite copy() {
        return new MeldedMoxite(this);
    }
}
