package mage.cards.s;

import mage.MageInt;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShadyTraveler extends CardImpl {

    public ShadyTraveler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.transformable = true;
        this.secondSideCardClazz = mage.cards.s.StalkingPredator.class;

        // Menace
        this.addAbility(new MenaceAbility());

        // Daybound
        this.addAbility(new TransformAbility());
        this.addAbility(new DayboundAbility());
    }

    private ShadyTraveler(final ShadyTraveler card) {
        super(card);
    }

    @Override
    public ShadyTraveler copy() {
        return new ShadyTraveler(this);
    }
}
