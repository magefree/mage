package mage.cards.q;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class QuarksBar extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
        new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public QuarksBar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control two or more other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // When this land enters untapped, choose one --
        // * This land deals 1 damage to each opponent.
        Ability ability = new EntersBattlefieldUntappedTriggeredAbility(new DamagePlayersEffect(1, TargetController.OPPONENT), false);

        // * Surveil 1.
        ability.addMode(new Mode(new SurveilEffect(1)));
        this.addAbility(ability);

        // {T}: Add {R}.
        this.addAbility(new RedManaAbility());
    }

    private QuarksBar(final QuarksBar card) {
        super(card);
    }

    @Override
    public QuarksBar copy() {
        return new QuarksBar(this);
    }
}
