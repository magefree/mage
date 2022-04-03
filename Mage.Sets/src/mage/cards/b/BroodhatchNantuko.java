package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectToken;

/**
 *
 * @author Temba21
 */
public final class BroodhatchNantuko extends CardImpl {

    public BroodhatchNantuko(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.INSECT, SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Broodhatch Nantuko is dealt damage, you may create that many 1/1 green Insect creature tokens.
        this.addAbility(new DealtDamageToSourceTriggeredAbility(
                new CreateTokenEffect(new InsectToken(), SavedDamageValue.MANY), true));

        // Morph {2}{G}
        this.addAbility(new MorphAbility(new ManaCostsImpl<>("{2}{G}")));
    }

    private BroodhatchNantuko(final BroodhatchNantuko card) {
        super(card);
    }

    @Override
    public BroodhatchNantuko copy() {
        return new BroodhatchNantuko(this);
    }
}
