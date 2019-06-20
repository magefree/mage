package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RetributiveWand extends CardImpl {

    public RetributiveWand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // {3}, {T}: Retributive Wand deals 1 damage to any target.
        Ability ability = new SimpleActivatedAbility(new DamageTargetEffect(1), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);

        // When Retributive Wand is put into a graveyard from the battlefield, it deals 5 damage to any target.
        ability = new PutIntoGraveFromBattlefieldSourceTriggeredAbility(
                new DamageTargetEffect(5, "it")
        );
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private RetributiveWand(final RetributiveWand card) {
        super(card);
    }

    @Override
    public RetributiveWand copy() {
        return new RetributiveWand(this);
    }
}
