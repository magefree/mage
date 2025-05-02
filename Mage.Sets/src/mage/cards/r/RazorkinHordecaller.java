package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.Gremlin11Token;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazorkinHordecaller extends CardImpl {

    public RazorkinHordecaller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLOWN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever you attack, create a 1/1 red Gremlin creature token.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new CreateTokenEffect(new Gremlin11Token()), 1));
    }

    private RazorkinHordecaller(final RazorkinHordecaller card) {
        super(card);
    }

    @Override
    public RazorkinHordecaller copy() {
        return new RazorkinHordecaller(this);
    }
}
