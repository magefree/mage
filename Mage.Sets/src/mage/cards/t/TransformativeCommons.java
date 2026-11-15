package mage.cards.t;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPlaneswalkerPermanent;

/**
 *
 * @author muz
 */
public final class TransformativeCommons extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledPlaneswalkerPermanent();
    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public TransformativeCommons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // This land enters tapped unless you control a planeswalker.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private TransformativeCommons(final TransformativeCommons card) {
        super(card);
    }

    @Override
    public TransformativeCommons copy() {
        return new TransformativeCommons(this);
    }
}
