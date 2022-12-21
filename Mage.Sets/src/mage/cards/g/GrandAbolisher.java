package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CantCastOrActivateOpponentsYourTurnEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author nantuko
 */
public final class GrandAbolisher extends CardImpl {

    public GrandAbolisher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // During your turn, your opponents can't cast spells or activate abilities of artifacts, creatures, or enchantments.
        this.addAbility(new SimpleStaticAbility(new CantCastOrActivateOpponentsYourTurnEffect()));
    }

    private GrandAbolisher(final GrandAbolisher card) {
        super(card);
    }

    @Override
    public GrandAbolisher copy() {
        return new GrandAbolisher(this);
    }
}
