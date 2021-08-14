package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SylvanShepherd extends CardImpl {

    public SylvanShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Sylvan Shepherd attacks, roll a d20.
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.addAbility(new AttacksTriggeredAbility(effect));

        // 1-9 | You gain 1 life.
        effect.addTableEntry(1, 9, new GainLifeEffect(1));

        // 10-19 | You gain 2 life.
        effect.addTableEntry(10, 19, new GainLifeEffect(2));

        // 20 | You gain 5 life.
        effect.addTableEntry(20, 20, new GainLifeEffect(5));
    }

    private SylvanShepherd(final SylvanShepherd card) {
        super(card);
    }

    @Override
    public SylvanShepherd copy() {
        return new SylvanShepherd(this);
    }
}
