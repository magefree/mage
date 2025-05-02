package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOrGraveyardOntoBattlefieldEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeauToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BonnyPallClearcutter extends CardImpl {

    public BonnyPallClearcutter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // When Bonny Pall, Clearcutter enters the battlefield, create Beau, a legendary blue Ox creature token with "This creature's power and toughness are each equal to the number of lands you control."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new BeauToken())));

        // Whenever you attack, draw a card, then you may put a land card from your hand or graveyard onto the battlefield.
        Ability ability = new AttacksWithCreaturesTriggeredAbility(new DrawCardSourceControllerEffect(1), 1);
        ability.addEffect(new PutCardFromHandOrGraveyardOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A, false).concatBy(", then"));
        this.addAbility(ability);
    }

    private BonnyPallClearcutter(final BonnyPallClearcutter card) {
        super(card);
    }

    @Override
    public BonnyPallClearcutter copy() {
        return new BonnyPallClearcutter(this);
    }
}
