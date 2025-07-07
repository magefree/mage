package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.effects.common.GainLifeAllEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author jonubuu
 */
public final class GroveOfTheBurnwillows extends CardImpl {

    public GroveOfTheBurnwillows(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Add {R} or {G}. Each opponent gains 1 life.
        Ability ability = new RedManaAbility();
        ability.addEffect(new GainLifeAllEffect(1, TargetController.OPPONENT));
        this.addAbility(ability);
        ability = new GreenManaAbility();
        ability.addEffect(new GainLifeAllEffect(1, TargetController.OPPONENT));
        this.addAbility(ability);
    }

    private GroveOfTheBurnwillows(final GroveOfTheBurnwillows card) {
        super(card);
    }

    @Override
    public GroveOfTheBurnwillows copy() {
        return new GroveOfTheBurnwillows(this);
    }
}
