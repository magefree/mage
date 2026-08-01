package mage.cards.g;

import java.util.UUID;

import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.TreasureToken;
import mage.target.TargetPlayer;

/**
 *
 * @author muz
 */
public final class GleamingSplendor extends CardImpl {

    public GleamingSplendor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Whenever an opponent draws their second card each turn, you create a Treasure token.
        this.addAbility(new DrawNthCardTriggeredAbility(
            new CreateTokenEffect(new TreasureToken()),
            false, TargetController.OPPONENT, 2
        ));

        // {2}{W}: Two target players each draw a card.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardTargetEffect(1).setText("Two target players each draw a card"),
            new ManaCostsImpl<>("{2}{W}")
        );
        ability.addTarget(new TargetPlayer(2));
        this.addAbility(ability);
    }

    private GleamingSplendor(final GleamingSplendor card) {
        super(card);
    }

    @Override
    public GleamingSplendor copy() {
        return new GleamingSplendor(this);
    }
}
