package mage.cards.l;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.condition.common.OpponentHasMoreLifeCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Angel33Token;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LinvalaThePreserver extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_PERMANENT_CREATURES);

    public LinvalaThePreserver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Linvala, the Preserver enters the battlefield, if an opponent has more life than you, you gain 5 life.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GainLifeEffect(5))
                .withInterveningIf(OpponentHasMoreLifeCondition.instance));

        // When Linvala enters the battlefield, if an opponent controls more creatures than you, create a 3/3 white Angel creature token with flying.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Angel33Token()))
                .withInterveningIf(condition));
    }

    private LinvalaThePreserver(final LinvalaThePreserver card) {
        super(card);
    }

    @Override
    public LinvalaThePreserver copy() {
        return new LinvalaThePreserver(this);
    }
}
