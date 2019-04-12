
package mage.cards.k;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;

/**
 *
 * @author Ketsuban
 */
public final class KnightOfTheKitchenSinkD extends CardImpl {

    private static final FilterObject filter = new FilterObject("odd collector numbers");

    static {
        filter.add(new KnightOfTheKitchenSinkDPredicate());
    }

    public KnightOfTheKitchenSinkD(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.CYBORG);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Protection from odd collector numbers
        this.addAbility(new ProtectionAbility(filter));
    }

    public KnightOfTheKitchenSinkD(final KnightOfTheKitchenSinkD card) {
        super(card);
    }

    @Override
    public KnightOfTheKitchenSinkD copy() {
        return new KnightOfTheKitchenSinkD(this);
    }
}

class KnightOfTheKitchenSinkDPredicate implements Predicate<MageObject> {
    
    @Override
    public boolean apply(MageObject input, Game game) {
        if (input instanceof Card) {
            return (parseCardNumber(((Card) input).getCardNumber()) & 1) == 1;
        }
        if (input instanceof Ability) {
            return (parseCardNumber(game.getPermanent(((Ability) input).getSourceId()).getCardNumber()) & 1) == 1;
        }
        return false;
    }
    
    public int parseCardNumber(String input) {
        Matcher matcher = Pattern.compile("\\d+").matcher(input);
        matcher.find();
        return Integer.valueOf(matcher.group());
    }
}
