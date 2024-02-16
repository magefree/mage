
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.ShuffleIntoLibraryGraveOfSourceOwnerEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.target.TargetPermanent;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public final class UlamogTheInfiniteGyre extends CardImpl {

    public UlamogTheInfiniteGyre(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{11}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);

        this.power = new MageInt(10);
        this.toughness = new MageInt(10);

        // When you cast Ulamog, the Infinite Gyre, destroy target permanent.
        Ability ability = new CastSourceTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent());
        this.addAbility(ability);

        // Annihilator 4 (Whenever this creature attacks, defending player sacrifices four permanents.)
        this.addAbility(new AnnihilatorAbility(4));
        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());
        // When Ulamog is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibraryGraveOfSourceOwnerEffect(), false));
    }

    private UlamogTheInfiniteGyre(final UlamogTheInfiniteGyre card) {
        super(card);
    }

    @Override
    public UlamogTheInfiniteGyre copy() {
        return new UlamogTheInfiniteGyre(this);
    }
}
