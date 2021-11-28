
package mage.cards.d;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TransformAbility;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.permanent.token.DefenderPlantToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

/**
 * @author TheElk801
 */
public final class DowsingDagger extends CardImpl {

    public DowsingDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        this.secondSideCardClazz = mage.cards.l.LostVale.class;

        // When Dowsing Dagger enters the battlefield, target opponent creates two 0/2 green Plant creature tokens with defender.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new DefenderPlantToken(), 2), false);
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.addAbility(ability);

        // Equipped creature gets +2/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(2, 1)));

        // Whenever equipped creature deals combat damage to a player, you may transform Dowsing Dagger.
        this.addAbility(new TransformAbility());
        this.addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(new TransformSourceEffect(), "equipped", true));

        // Equip 2
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2)));
    }

    private DowsingDagger(final DowsingDagger card) {
        super(card);
    }

    @Override
    public DowsingDagger copy() {
        return new DowsingDagger(this);
    }
}