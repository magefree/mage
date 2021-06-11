package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.PhyrexianGolemToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PhyrexianTriniform extends CardImpl {

    public PhyrexianTriniform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{9}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(9);
        this.toughness = new MageInt(9);

        // When Phyrexian Triniform dies, create three 3/3 colorless Golem artifact creature tokens.
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new PhyrexianGolemToken(), 3)));

        // Encore {12}
        this.addAbility(new EncoreAbility(new GenericManaCost(12)));
    }

    private PhyrexianTriniform(final PhyrexianTriniform card) {
        super(card);
    }

    @Override
    public PhyrexianTriniform copy() {
        return new PhyrexianTriniform(this);
    }
}
