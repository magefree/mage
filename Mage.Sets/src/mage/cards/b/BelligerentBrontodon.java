package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BelligerentBrontodon extends CardImpl {

    public BelligerentBrontodon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{W}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Each creature you control assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessControlledEffect()));
    }

    private BelligerentBrontodon(final BelligerentBrontodon card) {
        super(card);
    }

    @Override
    public BelligerentBrontodon copy() {
        return new BelligerentBrontodon(this);
    }
}
