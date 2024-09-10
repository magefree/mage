package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.MonarchIsSourceControllerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.BecomesMonarchSourceEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.hint.common.MonarchHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.permanent.token.Knight31RedToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class CourtOfEmbereth extends CardImpl {

    public CourtOfEmbereth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{R}{R}");


        // When Court of Embereth enters the battlefield, you become the monarch.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new BecomesMonarchSourceEffect()).addHint(MonarchHint.instance));

        //  At the beginning of your upkeep, create a 3/1 red Knight creature token. Then if you're the monarch, Court of Embereth deals X damage to each opponent, where X is the number of creatures you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenEffect(new Knight31RedToken()),
                TargetController.YOU, false
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new DamagePlayersEffect(CreaturesYouControlCount.instance, TargetController.OPPONENT)
                        .setText("{this} deals X damage to each opponent, where X is the number of creatures you control"),
                MonarchIsSourceControllerCondition.instance
        ).concatBy("Then"));
        ability.addHint(CreaturesYouControlHint.instance);
        ability.addHint(MonarchHint.instance);
        this.addAbility(ability);
    }

    private CourtOfEmbereth(final CourtOfEmbereth card) {
        super(card);
    }

    @Override
    public CourtOfEmbereth copy() {
        return new CourtOfEmbereth(this);
    }
}
