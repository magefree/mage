package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.HumanCitizenToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SpiderGirlLegacyHero extends CardImpl {

    public SpiderGirlLegacyHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SPIDER);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // During your turn, Spider-Girl has flying.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                MyTurnCondition.instance, "during your turn, {this} has flying"
        )));

        // When Spider-Girl leaves the battlefield, create a 1/1 green and white Human Citizen creature token.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new CreateTokenEffect(new HumanCitizenToken())));
    }

    private SpiderGirlLegacyHero(final SpiderGirlLegacyHero card) {
        super(card);
    }

    @Override
    public SpiderGirlLegacyHero copy() {
        return new SpiderGirlLegacyHero(this);
    }
}
