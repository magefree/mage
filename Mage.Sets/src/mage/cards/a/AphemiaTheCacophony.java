package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.common.FilterEnchantmentCard;
import mage.game.permanent.token.ZombieToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AphemiaTheCacophony extends CardImpl {

    private static final FilterCard filter = new FilterEnchantmentCard("an enchantment card");

    public AphemiaTheCacophony(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HARPY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // At the beginning of your end step, you may exile an enchantment card from your graveyard. If you do, create a 2/2 black Zombie creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(new DoIfCostPaid(
                new CreateTokenEffect(new ZombieToken()),
                new ExileFromGraveCost(new TargetCardInYourGraveyard(filter))
        ), TargetController.YOU, false));
    }

    private AphemiaTheCacophony(final AphemiaTheCacophony card) {
        super(card);
    }

    @Override
    public AphemiaTheCacophony copy() {
        return new AphemiaTheCacophony(this);
    }
}
