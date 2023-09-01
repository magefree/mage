package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAttachedTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.DefenderPlantToken;
import mage.target.Target;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DowsingDagger extends TransformingDoubleFacedCard {

    public DowsingDagger(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.ARTIFACT}, new SubType[]{SubType.EQUIPMENT}, "{2}",
                "Lost Vale",
                new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // When Dowsing Dagger enters the battlefield, target opponent creates two 0/2 green Plant creature tokens with defender.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new CreateTokenTargetEffect(new DefenderPlantToken(), 2), false
        );
        Target target = new TargetOpponent();
        ability.addTarget(target);
        this.getLeftHalfCard().addAbility(ability);

        // Equipped creature gets +2/+1.
        this.getLeftHalfCard().addAbility(new SimpleStaticAbility(new BoostEquippedEffect(2, 1)));

        // Whenever equipped creature deals combat damage to a player, you may transform Dowsing Dagger.
        this.getLeftHalfCard().addAbility(new DealsDamageToAPlayerAttachedTriggeredAbility(
                new TransformSourceEffect(), "equipped", true
        ));

        // Equip 2
        this.getLeftHalfCard().addAbility(new EquipAbility(2, false));

        // Lost Vale
        // (Transforms from Dowsing Dagger.)
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new InfoEffect("<i>(Transforms from Dowsing Dagger.)</i>")));

        // T: Add three mana of any one color.
        this.getRightHalfCard().addAbility(new SimpleManaAbility(
                Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(3), new TapSourceCost()
        ));
    }

    private DowsingDagger(final DowsingDagger card) {
        super(card);
    }

    @Override
    public DowsingDagger copy() {
        return new DowsingDagger(this);
    }
}
