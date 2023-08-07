package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.PowerstoneToken;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThranSpider extends CardImpl {

    public ThranSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Thran Spider enters the battlefield, you and target opponent each create a tapped Powerstone token.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(
                new PowerstoneToken(), 1, true
        ).setText("you"));
        ability.addEffect(new CreateTokenTargetEffect(
                new PowerstoneToken(), 1, true
        ).setText("and target opponent each create a tapped Powerstone token"));
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {7}: Look at the top four cards of your library. You may reveal an artifact card from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.addAbility(new SimpleActivatedAbility(
                new LookLibraryAndPickControllerEffect(
                        4, 1,
                        StaticFilters.FILTER_CARD_ARTIFACT_AN,
                        PutCards.HAND, PutCards.BOTTOM_RANDOM
                ), new GenericManaCost(7)
        ));
    }

    private ThranSpider(final ThranSpider card) {
        super(card);
    }

    @Override
    public ThranSpider copy() {
        return new ThranSpider(this);
    }
}
