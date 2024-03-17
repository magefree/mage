package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class CurieEmergentIntelligence extends CardImpl {

    public CurieEmergentIntelligence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}{U}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ROBOT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Curie, Emergent Intelligence deals combat damage to a player, draw cards equal to its base power.
        // {1}{U}, Exile another nontoken artifact creature you control: Curie becomes a copy of the exiled creature, except it has "Whenever this creature deals combat damage to a player, draw cards equal to its base power."
    }

    private CurieEmergentIntelligence(final CurieEmergentIntelligence card) {
        super(card);
    }

    @Override
    public CurieEmergentIntelligence copy() {
        return new CurieEmergentIntelligence(this);
    }
}
