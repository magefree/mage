
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.PutIntoGraveFromAnywhereSourceTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.ShuffleIntoLibraryGraveOfSourceOwnerEffect;
import mage.abilities.effects.common.turn.AddExtraTurnControllerEffect;
import mage.abilities.keyword.AnnihilatorAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorlessPredicate;

/**
 * @author Loki
 */
public final class EmrakulTheAeonsTorn extends CardImpl {

    private static final FilterStackObject filter = new FilterStackObject("spells that are one or more colors");

    static {
        filter.add(Predicates.not(ColorlessPredicate.instance));
    }

    public EmrakulTheAeonsTorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{15}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELDRAZI);
        this.power = new MageInt(15);
        this.toughness = new MageInt(15);

        // Emrakul, the Aeons Torn can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        // When you cast Emrakul, take an extra turn after this one.
        this.addAbility(new CastSourceTriggeredAbility(new AddExtraTurnControllerEffect()));

        // Flying, protection from colored spells, annihilator 6
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new ProtectionAbility(filter));
        this.addAbility(new AnnihilatorAbility(6));
        // When Emrakul is put into a graveyard from anywhere, its owner shuffles their graveyard into their library.
        this.addAbility(new PutIntoGraveFromAnywhereSourceTriggeredAbility(new ShuffleIntoLibraryGraveOfSourceOwnerEffect(), false));
    }

    private EmrakulTheAeonsTorn(final EmrakulTheAeonsTorn card) {
        super(card);
    }

    @Override
    public EmrakulTheAeonsTorn copy() {
        return new EmrakulTheAeonsTorn(this);
    }
}
