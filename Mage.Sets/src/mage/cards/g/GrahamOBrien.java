package mage.cards.g;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DoctorsCompanionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.permanent.token.FoodToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class GrahamOBrien extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public GrahamOBrien(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, create a Food token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new FoodToken()),
                filter, false
        ).setAbilityWord(AbilityWord.PARADOX));

        // Doctor's companion
        this.addAbility(DoctorsCompanionAbility.getInstance());
    }

    private GrahamOBrien(final GrahamOBrien card) {
        super(card);
    }

    @Override
    public GrahamOBrien copy() {
        return new GrahamOBrien(this);
    }
}
