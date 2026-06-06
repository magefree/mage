package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardSetInfo;
import mage.cards.OmenCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class DisruptiveStormbrood extends OmenCard {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature with power 3 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.OR_LESS, 3));
    }

    public DisruptiveStormbrood(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.DRAGON}, "{4}{G}",
                "Petty Revenge",
                new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Disruptive Stormbrood
        this.getLeftHalfCard().setPT(3, 3);

        // Flying
        this.getLeftHalfCard().addAbility(FlyingAbility.getInstance());

        // When this creature enters, destroy up to one target artifact or enchantment.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_OR_ENCHANTMENT));
        this.getLeftHalfCard().addAbility(ability);

        // Petty Revenge
        // Destroy target creature with power 3 or less.
        Effect spellEffect = new DestroyTargetEffect();
        this.getRightHalfCard().getSpellAbility().addEffect(spellEffect);
        this.getRightHalfCard().getSpellAbility().addTarget(new TargetPermanent(filter));

        finalizeCard();
    }

    private DisruptiveStormbrood(final DisruptiveStormbrood card) {
        super(card);
    }

    @Override
    public DisruptiveStormbrood copy() {
        return new DisruptiveStormbrood(this);
    }
}
