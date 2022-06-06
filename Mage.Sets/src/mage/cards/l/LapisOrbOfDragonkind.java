package mage.cards.l;

import mage.abilities.common.delayed.ManaSpentDelayedTriggeredAbility;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.abilities.mana.BlueManaAbility;
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
public final class LapisOrbOfDragonkind extends CardImpl {

    private static final FilterSpell filter = new FilterCreatureSpell("a Dragon creature spell");

    static {
        filter.add(SubType.DRAGON.getPredicate());
    }

    public LapisOrbOfDragonkind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}{U}");

        // {T}: Add {U}. When you spend this mana to cast a Dragon creature spell, scry 2.
        BasicManaAbility ability = new BlueManaAbility();
        ability.addEffect(new CreateDelayedTriggeredAbilityEffect(
                new ManaSpentDelayedTriggeredAbility(new ScryEffect(2), filter)
        ));
        ability.setUndoPossible(false);
        this.addAbility(ability);
    }

    private LapisOrbOfDragonkind(final LapisOrbOfDragonkind card) {
        super(card);
    }

    @Override
    public LapisOrbOfDragonkind copy() {
        return new LapisOrbOfDragonkind(this);
    }
}
