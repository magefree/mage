package mage.cards.i;

import java.util.UUID;
import mage.constants.SubType;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.CreateTokenCopySourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.hint.common.ArtifactYouControlHint;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ImprovisedArsenal extends CardImpl {

    private static final DynamicValue count = new PermanentsOnBattlefieldCount(new FilterControlledArtifactPermanent());

    public ImprovisedArsenal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{R}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+0 for each artifact you control.
        this.addAbility(new SimpleStaticAbility(
            new BoostEquippedEffect(count, StaticValue.get(0))
        ).addHint(ArtifactYouControlHint.instance));

        // {4}{R}: Create a token that's a copy of this Equipment.
        this.addAbility(new SimpleActivatedAbility(
            new CreateTokenCopySourceEffect(), new ManaCostsImpl<>("{4}{R}")
        ));

        // Equip {R}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl<>("{R}"), false));
    }

    private ImprovisedArsenal(final ImprovisedArsenal card) {
        super(card);
    }

    @Override
    public ImprovisedArsenal copy() {
        return new ImprovisedArsenal(this);
    }
}
