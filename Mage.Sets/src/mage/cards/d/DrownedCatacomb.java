package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTappedUnlessAbility;
import mage.abilities.condition.common.YouControlPermanentCondition;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class DrownedCatacomb extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("an Island or a Swamp");

    static {
        filter.add(Predicates.or(SubType.ISLAND.getPredicate(), SubType.SWAMP.getPredicate()));
    }

    private static final YouControlPermanentCondition condition = new YouControlPermanentCondition(filter);

    public DrownedCatacomb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, null);

        // Drowned Catacomb enters the battlefield tapped unless you control an Island or a Swamp.
        this.addAbility(new EntersBattlefieldTappedUnlessAbility(condition).addHint(condition.getHint()));

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private DrownedCatacomb(final DrownedCatacomb card) {
        super(card);
    }

    @Override
    public DrownedCatacomb copy() {
        return new DrownedCatacomb(this);
    }
}
