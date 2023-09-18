package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrainingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Boar3Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RuralRecruit extends CardImpl {

    public RuralRecruit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Training
        this.addAbility(new TrainingAbility());

        // When Rural Recruit enters the battlefield, create a 3/1 Boar creature token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Boar3Token())));
    }

    private RuralRecruit(final RuralRecruit card) {
        super(card);
    }

    @Override
    public RuralRecruit copy() {
        return new RuralRecruit(this);
    }
}
