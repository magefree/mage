package mage.cards.o;

import mage.abilities.common.EntersBattlefieldOrSpellCastControllerTriggeredAbilty;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.other.SpellZonePredicate;
import mage.game.permanent.token.OminousRoostToken;

import java.util.UUID;

/**
 * @author LePwnerer
 */
public final class OminousRoost extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from your graveyard");

    static {
        filter.add(new SpellZonePredicate(Zone.GRAVEYARD));
    }

    public OminousRoost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Ominous Roost enters the battlefield or whenever you cast a spell from your graveyard, create a 1/1 blue Bird creature token with flying and "This creature can block only creatures with flying."
        this.addAbility(new EntersBattlefieldOrSpellCastControllerTriggeredAbilty(new CreateTokenEffect(new OminousRoostToken()), filter, false));
    }

    private OminousRoost(final OminousRoost card) {
        super(card);
    }

    @Override
    public OminousRoost copy() {
        return new OminousRoost(this);
    }
}
