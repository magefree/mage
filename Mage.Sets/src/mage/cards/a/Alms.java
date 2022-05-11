package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileTopCardOfGraveyardCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class Alms extends CardImpl {

    public Alms(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // {1}, Exile the top card of your graveyard: Prevent the next 1 damage that would be dealt to target creature this turn.
        Ability ability = new SimpleActivatedAbility(
                new PreventDamageToTargetEffect(Duration.EndOfTurn, 1), new GenericManaCost(1)
        );
        ability.addCost(new ExileTopCardOfGraveyardCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Alms(final Alms card) {
        super(card);
    }

    @Override
    public Alms copy() {
        return new Alms(this);
    }
}
