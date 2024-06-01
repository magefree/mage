package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.target.TargetPlayer;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author grimreap124
 */
public final class Trenchpost extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.LOCUS, "Locus you control");

    public Trenchpost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.LAND }, "");

        this.subtype.add(SubType.LOCUS);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
        // {3}, {T}: Target player mills a card for each Locus you control.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new MillCardsTargetEffect(new PermanentsOnBattlefieldCount(filter)), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private Trenchpost(final Trenchpost card) {
        super(card);
    }

    @Override
    public Trenchpost copy() {
        return new Trenchpost(this);
    }
}
