package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.GoadAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BloodthirstyBlade extends CardImpl {

    public BloodthirstyBlade(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +2/+0 and is goaded.
        Ability ability = new SimpleStaticAbility(new BoostEquippedEffect(2, 0));
        ability.addEffect(new GoadAttachedEffect());
        this.addAbility(ability);

        // {1}: Attach Bloodthirsty Blade to target creature an opponent controls. Active this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD,
                new AttachEffect(
                        Outcome.Detriment, "Attach {this} to target creature an opponent controls"
                ), new GenericManaCost(1)
        );
        ability.addTarget(new TargetOpponentsCreaturePermanent());
        this.addAbility(ability);
    }

    private BloodthirstyBlade(final BloodthirstyBlade card) {
        super(card);
    }

    @Override
    public BloodthirstyBlade copy() {
        return new BloodthirstyBlade(this);
    }
}
