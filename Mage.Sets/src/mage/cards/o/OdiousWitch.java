package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OdiousWitch extends CardImpl {

    public OdiousWitch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlack(true);
        this.nightCard = true;

        // Whenever Odious Witch attacks, defending player loses 1 life and you gain 1 life.
        Ability ability = new AttacksTriggeredAbility(
                new LoseLifeTargetEffect(1)
                        .setText("defending player loses 1 life"),
                false, null, SetTargetPointer.PLAYER
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private OdiousWitch(final OdiousWitch card) {
        super(card);
    }

    @Override
    public OdiousWitch copy() {
        return new OdiousWitch(this);
    }
}
