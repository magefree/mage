package mage.cards.c;

import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrystalFragments extends CardImpl {

    public CrystalFragments(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{W}");

        this.subtype.add(SubType.EQUIPMENT);
        this.secondSideCardClazz = mage.cards.s.SummonAlexander.class;

        // Equipped creature gets +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostEquippedEffect(1, 1)));

        // {5}{W}{W}: Exile this Equipment, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        this.addAbility(new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{5}{W}{W}")
        ));

        // Equip {1}
        this.addAbility(new EquipAbility(1));
    }

    private CrystalFragments(final CrystalFragments card) {
        super(card);
    }

    @Override
    public CrystalFragments copy() {
        return new CrystalFragments(this);
    }
}
