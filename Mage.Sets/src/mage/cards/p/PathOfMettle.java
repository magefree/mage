package mage.cards.p;

import java.util.UUID;

import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TransformAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;

/**
 * @author LevelX2
 */
public final class PathOfMettle extends CardImpl {

    private static final FilterCreaturePermanent filterDamage = new FilterCreaturePermanent("creature that doesn't have first strike, double strike, vigilance, or haste");
    private static final FilterCreaturePermanent filterTransform = new FilterCreaturePermanent("creatures that have first strike, double strike, vigilance, and/or haste");

    static {
        filterDamage.add(Predicates.not(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        )));

        filterTransform.add(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
    }

    public PathOfMettle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);

        this.secondSideCardClazz = mage.cards.m.MetzaliTowerOfTriumph.class;

        // When Path of Mettle enters the battlefield, it deals 1 damage to each creature that doesn't have first strike, double strike, vigilance, or haste.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, filterDamage)));

        // Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, transform Path of Mettle.
        this.addAbility(new TransformAbility());
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(new TransformSourceEffect(), 2, filterTransform)
                .setTriggerPhrase("Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, "));
    }

    private PathOfMettle(final PathOfMettle card) {
        super(card);
    }

    @Override
    public PathOfMettle copy() {
        return new PathOfMettle(this);
    }
}
