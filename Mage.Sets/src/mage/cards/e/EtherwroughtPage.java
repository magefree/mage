package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class EtherwroughtPage extends CardImpl {

    public EtherwroughtPage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}{U}{B}");

        // At the beginning of your upkeep, choose one - You gain 2 life; or look at the top card of your library, then you may put that card into your graveyard; or each opponent loses 1 life.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new GainLifeEffect(2), TargetController.YOU, false
        );

        // or look at the top card of your library, then you may put that card into your graveyard;
        ability.addMode(new Mode(new SurveilEffect(1)));

        // or each opponent loses 1 life
        ability.addMode(new Mode(new LoseLifeOpponentsEffect(1)));

        this.addAbility(ability);
    }

    private EtherwroughtPage(final EtherwroughtPage card) {
        super(card);
    }

    @Override
    public EtherwroughtPage copy() {
        return new EtherwroughtPage(this);
    }
}
