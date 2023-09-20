package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessTargetEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WalkingBulwark extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public WalkingBulwark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}: Until end of turn, target creature with defender gains haste, can attack as though it didn't have defender, and assigns combat damage equal to its toughness rather than its power. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn).setText("until end of turn, target creature with defender gains haste"), new GenericManaCost(2));
        ability.addEffect(new CanAttackAsThoughItDidntHaveDefenderTargetEffect(Duration.EndOfTurn).setText(", can attack as though it didn't have defender"));
        ability.addEffect(new CombatDamageByToughnessTargetEffect(Duration.EndOfTurn).setText(", and assigns combat damage equal to its toughness rather than its power"));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private WalkingBulwark(final WalkingBulwark card) {
        super(card);
    }

    @Override
    public WalkingBulwark copy() {
        return new WalkingBulwark(this);
    }
}
