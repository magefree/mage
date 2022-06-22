package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterCreatureSpell;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ScaledNurturer extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a Dragon creature spell");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public ScaledNurturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // {T}: Add {G}. When you spend this mana to cast a Dragon creature spell, you gain 2 life.
        BasicManaAbility ability = new GreenManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new ManaSpentDelayedTriggeredAbility(new GainLifeEffect(2), filter)
        ));
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private ScaledNurturer(final ScaledNurturer card) {
        super(card);
    }

    @Override
    public ScaledNurturer copy() {
        return new ScaledNurturer(this);
    }
}
