package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.MercenaryToken;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AngelicSellSword extends CardImpl {

    public AngelicSellSword(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}");

        this.subtype.add(SubType.ANGEL);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Angelic Sell-Sword or another nontoken creature enters the battlefield under your control, create a 1/1 red Mercenary creature token with "{T}: Target creature you control gets +1/+0 until end of turn. Activate only as a sorcery."
        this.addAbility(new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new CreateTokenEffect(new MercenaryToken()),
                StaticFilters.FILTER_CREATURE_NON_TOKEN, false, true
        ));

        // Whenever Angelic Sell-Sword attacks, if its power is 6 or greater, draw a card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new DrawCardSourceControllerEffect(1)),
                AngelicSellSwordCondition.instance, "Whenever {this} attacks, " +
                "if its power is 6 or greater, draw a card."
        ));
    }

    private AngelicSellSword(final AngelicSellSword card) {
        super(card);
    }

    @Override
    public AngelicSellSword copy() {
        return new AngelicSellSword(this);
    }
}

enum AngelicSellSwordCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .orElse(0) >= 6;
    }
}
