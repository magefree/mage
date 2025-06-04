package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.OneOrMoreCombatDamagePlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.ThatPlayerControlsTargetAdjuster;

import java.util.UUID;

public final class FelineSovereign extends CardImpl {

    private static final FilterCreaturePermanent filterCat = new FilterCreaturePermanent("Cats");

    static {
        filterCat.add(SubType.CAT.getPredicate());
        filterCat.add(TargetController.YOU.getControllerPredicate());
    }

    private static final FilterCard filterProtectionFromDogs = new FilterCard("Dogs");

    static {
        filterProtectionFromDogs.add(SubType.DOG.getPredicate());
    }

    public FelineSovereign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Other Cats you control get +1/+1 and have protection from Dogs.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, filterCat, true));
        //
        Effect effect = new GainAbilityAllEffect(new ProtectionAbility(filterProtectionFromDogs), Duration.WhileOnBattlefield, filterCat, true);
        effect.setText("and have protection from Dogs");
        ability.addEffect(effect);
        this.addAbility(ability);

        // Whenever one or more Cats you control deal combat damage to a player, destroy up to one target artifact or enchantment that player controls.
        Ability ability2 = new OneOrMoreCombatDamagePlayerTriggeredAbility(new DestroyTargetEffect(), SetTargetPointer.PLAYER, filterCat, false);
        ability2.addTarget(new TargetPermanent(0, 1, new FilterArtifactOrEnchantmentPermanent("artifact or enchantment that player controls")));
        ability2.setTargetAdjuster(new ThatPlayerControlsTargetAdjuster());
        this.addAbility(ability2);
    }

    private FelineSovereign(final FelineSovereign card) {
        super(card);
    }

    @Override
    public FelineSovereign copy() {
        return new FelineSovereign(this);
    }
}
