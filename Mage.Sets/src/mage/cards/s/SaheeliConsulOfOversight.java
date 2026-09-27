package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.ThopterColorlessToken;
import mage.abilities.common.ScryOrSurveilTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SaheeliConsulOfOversight extends CardImpl {

    public SaheeliConsulOfOversight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever you scry or surveil, create a 1/1 colorless Thopter artifact creature token with flying. This ability triggers only once each turn.
        this.addAbility(new ScryOrSurveilTriggeredAbility(
            new CreateTokenEffect(new ThopterColorlessToken())
        ).setTriggersLimitEachTurn(1));
    }

    private SaheeliConsulOfOversight(final SaheeliConsulOfOversight card) {
        super(card);
    }

    @Override
    public SaheeliConsulOfOversight copy() {
        return new SaheeliConsulOfOversight(this);
    }
}
