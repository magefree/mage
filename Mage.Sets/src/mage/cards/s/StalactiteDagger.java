package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAllCreatureTypesAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.ShapeshifterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StalactiteDagger extends CardImpl {

    public StalactiteDagger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // When this Equipment enters, create a 1/1 colorless Shapeshifter creature token with changeling.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new ShapeshifterColorlessToken())));

        // Equipped creature gets +1/+1 and is all creature types.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(1, 1));
        ability.addEffect(new GainAllCreatureTypesAttachedEffect().setText("and is all creature types"));
        this.addAbility(ability);

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private StalactiteDagger(final StalactiteDagger card) {
        super(card);
    }

    @Override
    public StalactiteDagger copy() {
        return new StalactiteDagger(this);
    }
}
