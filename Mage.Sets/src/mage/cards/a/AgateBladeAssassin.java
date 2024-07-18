package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeDefendingPlayerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AgateBladeAssassin extends CardImpl {

    public AgateBladeAssassin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.LIZARD);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Agate-Blade Assassin attacks, defending player loses 1 life and you gain 1 life.
        Ability ability = new AttacksTriggeredAbility(new LoseLifeDefendingPlayerEffect(1, true));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private AgateBladeAssassin(final AgateBladeAssassin card) {
        super(card);
    }

    @Override
    public AgateBladeAssassin copy() {
        return new AgateBladeAssassin(this);
    }
}
