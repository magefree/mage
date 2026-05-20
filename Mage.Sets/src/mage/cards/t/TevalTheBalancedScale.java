package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.CardsLeaveGraveyardTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.ReturnCardChosenFromGraveyardEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.ZombieDruidToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TevalTheBalancedScale extends CardImpl {

    public TevalTheBalancedScale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Teval attacks, mill three cards. Then you may return a land card from your graveyard to the battlefield tapped.
        Ability ability = new AttacksTriggeredAbility(new MillCardsControllerEffect(3));
        ability.addEffect(new ReturnCardChosenFromGraveyardEffect(true, StaticFilters.FILTER_CARD_LAND_FROM_YOUR_GRAVEYARD,
                PutCards.BATTLEFIELD_TAPPED).concatBy("Then"));
        this.addAbility(ability);

        // Whenever one or more cards leave your graveyard, create a 2/2 black Zombie Druid creature token.
        this.addAbility(new CardsLeaveGraveyardTriggeredAbility(new CreateTokenEffect(new ZombieDruidToken())));
    }

    private TevalTheBalancedScale(final TevalTheBalancedScale card) {
        super(card);
    }

    @Override
    public TevalTheBalancedScale copy() {
        return new TevalTheBalancedScale(this);
    }
}
