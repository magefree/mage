package mage.cards.y;

import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.CompanionAbility;
import mage.abilities.keyword.CompanionCondition;
import mage.cards.Card;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class YorionSkyNomad extends CardImpl {

    public YorionSkyNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W/U}{W/U}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Companion — Your starting deck contains at least twenty cards more than the minimum deck size.
        this.addAbility(new CompanionAbility(YorionSkyNomadCompanionCondition.instance));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Yorion enters the battlefield, exile any number of other nonland permanents you own and control. Return those cards to the battlefield at the beginning of the next end step.
    }

    private YorionSkyNomad(final YorionSkyNomad card) {
        super(card);
    }

    @Override
    public YorionSkyNomad copy() {
        return new YorionSkyNomad(this);
    }
}
enum YorionSkyNomadCompanionCondition implements CompanionCondition{instance;

    @Override
    public String getRule() {
        return "Your starting deck contains at least twenty cards more than the minimum deck size.";
    }

    @Override
    public boolean isLegal(Set<Card> deck, int startingSize) {
        return deck.size()>=startingSize+20;
    }
}