package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.Predicates;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class SamutTyrantOfNaktamun extends CardImpl {

    private static final FilterNonlandCard filter = new FilterNonlandCard("instant and sorcery spells you control");

    static {
        filter.add(Predicates.or(CardType.INSTANT.getPredicate(), CardType.SORCERY.getPredicate()));
    }

    public SamutTyrantOfNaktamun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Instant and sorcery spells you control have split second.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new SplitSecondAbility(), filter)));
    }

    private SamutTyrantOfNaktamun(final SamutTyrantOfNaktamun card) {
        super(card);
    }

    @Override
    public SamutTyrantOfNaktamun copy() {
        return new SamutTyrantOfNaktamun(this);
    }
}
