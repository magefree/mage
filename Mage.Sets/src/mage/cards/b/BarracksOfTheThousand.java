package mage.cards.b;

import mage.abilities.common.CastSpellPaidBySourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.GnomeSoldierStarStarToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BarracksOfTheThousand extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an artifact or creature spell");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public BarracksOfTheThousand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        this.supertype.add(SuperType.LEGENDARY);

        // (Transforms from Thousand Moons Smithy.)
        this.nightCard = true;

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());

        // Whenever you cast an artifact or creature spell using mana produced by Barracks of the Thousand, create a white Gnome Soldier artifact creature token with "This creature's power and toughness are each equal to the number of artifacts and/or creatures you control."
        this.addAbility(new CastSpellPaidBySourceTriggeredAbility(
                new CreateTokenEffect(new GnomeSoldierStarStarToken()),
                filter, false
        ));
    }

    private BarracksOfTheThousand(final BarracksOfTheThousand card) {
        super(card);
    }

    @Override
    public BarracksOfTheThousand copy() {
        return new BarracksOfTheThousand(this);
    }
}