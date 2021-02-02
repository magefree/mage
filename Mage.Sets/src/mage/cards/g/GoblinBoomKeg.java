package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GoblinBoomKeg extends CardImpl {

    public GoblinBoomKeg(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // At the beginning of your upkeep, sacrifice Goblin Boom Keg.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeSourceEffect(), TargetController.YOU, false));

        // When Goblin Boom Keg is put into a graveyard from the battlefield, it deals 3 damage to any target.
        Ability ability = new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new DamageTargetEffect(3, "it"));
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private GoblinBoomKeg(final GoblinBoomKeg card) {
        super(card);
    }

    @Override
    public GoblinBoomKeg copy() {
        return new GoblinBoomKeg(this);
    }
}
