package mage.cards.f;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.common.EntersBattlefieldUntappedTriggeredAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;

import java.util.UUID;

/**
 * @author muz
 */
public final class FederationOutpost extends CardImpl {

    private static final FilterLandPermanent filter = new FilterLandPermanent("other lands");

    static {
        filter.add(AnotherPredicate.instance);
    }

    private static final YouControlPermanentCondition condition =
        new YouControlPermanentCondition(filter, ComparisonType.OR_GREATER, 2);

    public FederationOutpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control two or more other lands.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // When this land enters untapped, choose one --
        // * You gain 2 life.
        Ability ability = new EntersBattlefieldUntappedTriggeredAbility(new GainLifeEffect(2), false);

        // * Surveil 1.
        ability.addMode(new Mode(new SurveilEffect(1)));
        this.addAbility(ability);

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private FederationOutpost(final FederationOutpost card) {
        super(card);
    }

    @Override
    public FederationOutpost copy() {
        return new FederationOutpost(this);
    }
}
