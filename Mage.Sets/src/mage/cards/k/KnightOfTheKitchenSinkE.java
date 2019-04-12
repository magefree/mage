
package mage.cards.k;

import java.util.UUID;
import java.util.regex.Pattern;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.stack.Spell;

/**
 *
 * @author Ketsuban
 */
public final class KnightOfTheKitchenSinkE extends CardImpl {

    private static final FilterObject filter = new FilterObject("two-word names");

    static {
        filter.add(new KnightOfTheKitchenSinkEPredicate());
    }

    public KnightOfTheKitchenSinkE(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{W}{W}");
        this.subtype.add(SubType.CYBORG);
        this.subtype.add(SubType.KNIGHT);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Protection from two-word names
        this.addAbility(new ProtectionAbility(filter));
    }

    public KnightOfTheKitchenSinkE(final KnightOfTheKitchenSinkE card) {
        super(card);
    }

    @Override
    public KnightOfTheKitchenSinkE copy() {
        return new KnightOfTheKitchenSinkE(this);
    }
}

class KnightOfTheKitchenSinkEPredicate implements Predicate<MageObject> {

    public KnightOfTheKitchenSinkEPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        String name = input.getName();
        if (input instanceof SplitCard) {
            return hasTwoWords(((SplitCard)input).getLeftHalfCard().getName()) || hasTwoWords(((SplitCard)input).getRightHalfCard().getName());
        } else if (input instanceof Spell && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED){
            SplitCard card = (SplitCard) ((Spell)input).getCard();
            return hasTwoWords(card.getLeftHalfCard().getName()) || hasTwoWords(card.getRightHalfCard().getName());
        } else {
            if (name.contains(" // ")) {
                String leftName = name.substring(0, name.indexOf(" // "));
                String rightName = name.substring(name.indexOf(" // ") + 4, name.length());
                return hasTwoWords(leftName) || hasTwoWords(rightName);
            } else {
                return hasTwoWords(name);
            }
        }
    }

    private boolean hasTwoWords(String str) {
        return Pattern.compile("\\s+").split(str).length == 2;
    }

    @Override
    public String toString() {
        return "";
    }
}
