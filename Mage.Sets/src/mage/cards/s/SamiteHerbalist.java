package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTappedSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamiteHerbalist extends CardImpl {

    public SamiteHerbalist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Samite Herbalist becomes tapped, you gain 1 life and scry 1.
        Ability ability = new BecomesTappedSourceTriggeredAbility(new GainLifeEffect(1));
        ability.addEffect(new ScryEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private SamiteHerbalist(final SamiteHerbalist card) {
        super(card);
    }

    @Override
    public SamiteHerbalist copy() {
        return new SamiteHerbalist(this);
    }
}
