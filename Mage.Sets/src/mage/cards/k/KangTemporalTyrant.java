package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.DrawNthCardTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ConniveSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class KangTemporalTyrant extends CardImpl {

    public KangTemporalTyrant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Kang attacks, he connives.
        this.addAbility(new AttacksTriggeredAbility(new ConniveSourceEffect("he")));

        // Whenever you draw your second card each turn, each opponent loses 1 life and you gain 1 life.
        Ability ability = new DrawNthCardTriggeredAbility(new LoseLifeOpponentsEffect(1), false, 2);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private KangTemporalTyrant(final KangTemporalTyrant card) {
        super(card);
    }

    @Override
    public KangTemporalTyrant copy() {
        return new KangTemporalTyrant(this);
    }
}
