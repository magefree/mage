package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtchedFamiliar extends CardImpl {

    public EtchedFamiliar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.FOX);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Etched Familiar dies, each opponent loses 2 life and you gain 2 life.
        Ability ability = new DiesSourceTriggeredAbility(new LoseLifeOpponentsEffect(2));
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private EtchedFamiliar(final EtchedFamiliar card) {
        super(card);
    }

    @Override
    public EtchedFamiliar copy() {
        return new EtchedFamiliar(this);
    }
}
