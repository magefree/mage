package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Poxwalkers extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public Poxwalkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Curse of the Walking Pox -- Whenever you cast a spell from anywhere other than your hand, return Poxwalkers from your graveyard to the battlefield tapped.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true),
                filter, false, false
        ).withFlavorWord("Curse of the Walking Pox"));
    }

    private Poxwalkers(final Poxwalkers card) {
        super(card);
    }

    @Override
    public Poxwalkers copy() {
        return new Poxwalkers(this);
    }
}
