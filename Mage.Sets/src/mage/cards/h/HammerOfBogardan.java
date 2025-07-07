package mage.cards.h;

import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class HammerOfBogardan extends CardImpl {

    public HammerOfBogardan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}{R}");

        // Hammer of Bogardan deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // {2}{R}{R}{R}: Return Hammer of Bogardan from your graveyard to your hand. Activate this ability only during your upkeep.
        this.addAbility(new ActivateIfConditionActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                new ManaCostsImpl<>("{2}{R}{R}{R}"), IsStepCondition.getMyUpkeep()
        ));
    }

    private HammerOfBogardan(final HammerOfBogardan card) {
        super(card);
    }

    @Override
    public HammerOfBogardan copy() {
        return new HammerOfBogardan(this);
    }
}
