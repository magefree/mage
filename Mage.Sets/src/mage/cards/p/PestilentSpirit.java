package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.GainAbilitySpellsEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PestilentSpirit extends CardImpl {

    private static final FilterObject filter = new FilterObject("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)
        ));
    }

    public PestilentSpirit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Menace
        this.addAbility(new MenaceAbility());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // Instant and sorcery spells you control have deathtouch.
        this.addAbility(new SimpleStaticAbility(
                Zone.BATTLEFIELD,
                new GainAbilitySpellsEffect(
                        DeathtouchAbility.getInstance(), filter
                ).setText("Instant and sorcery spells you control have deathtouch")
        ));
    }

    private PestilentSpirit(final PestilentSpirit card) {
        super(card);
    }

    @Override
    public PestilentSpirit copy() {
        return new PestilentSpirit(this);
    }
}
