package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToACreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.LoseLifeTargetControllerEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FlayedNim extends CardImpl {

    public FlayedNim(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");

        this.subtype.add(SubType.SKELETON);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Flayed Nim deals combat damage to a creature, that creature's controller loses that much life.
        this.addAbility(new DealsCombatDamageToACreatureTriggeredAbility(
                new LoseLifeTargetControllerEffect(SavedDamageValue.MUCH)
                .setText("that creature's controller loses that much life"),
                false, true));

        // {2}{B}: Regenerate Flayed Nim.
        this.addAbility(new SimpleActivatedAbility(new RegenerateSourceEffect(), new ManaCostsImpl("{2}{B}")));
    }

    private FlayedNim(final FlayedNim card) {
        super(card);
    }

    @Override
    public FlayedNim copy() {
        return new FlayedNim(this);
    }
}
