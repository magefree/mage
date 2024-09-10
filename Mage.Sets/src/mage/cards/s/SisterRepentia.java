package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.MiracleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SisterRepentia extends CardImpl {

    public SisterRepentia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Martyrdom -- When Sister Repentia dies, you gain 2 life and draw two cards.
        Ability ability = new DiesSourceTriggeredAbility(new GainLifeEffect(2));
        ability.addEffect(new DrawCardSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability.withFlavorWord("Martyrdom"));

        // Miracle {W}{B}
        this.addAbility(new MiracleAbility("{W}{B}"));
    }

    private SisterRepentia(final SisterRepentia card) {
        super(card);
    }

    @Override
    public SisterRepentia copy() {
        return new SisterRepentia(this);
    }
}
