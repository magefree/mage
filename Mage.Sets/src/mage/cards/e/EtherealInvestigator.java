package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.DrawCardTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.SpiritWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtherealInvestigator extends CardImpl {

    public EtherealInvestigator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Ethereal Investigator enters the battlefield, investigate X times, where X is the number of opponents you have.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new InvestigateEffect(OpponentsCount.instance)));

        // Whenever you draw your second card each turn, create a 1/1 white Spirit creature token with flying.
        this.addAbility(new DrawCardTriggeredAbility(
                new CreateTokenEffect(new SpiritWhiteToken()), false, 2
        ));
    }

    private EtherealInvestigator(final EtherealInvestigator card) {
        super(card);
    }

    @Override
    public EtherealInvestigator copy() {
        return new EtherealInvestigator(this);
    }
}
