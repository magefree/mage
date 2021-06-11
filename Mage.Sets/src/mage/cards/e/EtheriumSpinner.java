package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EtheriumSpinner extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with mana value 4 or greater");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.MORE_THAN, 3));
    }

    public EtheriumSpinner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever you cast a spell with mana value 4 or greater, create a 1/1 colorless Thopter artifact creature token with flying.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new ThopterColorlessToken()), filter, false
        ));
    }

    private EtheriumSpinner(final EtheriumSpinner card) {
        super(card);
    }

    @Override
    public EtheriumSpinner copy() {
        return new EtheriumSpinner(this);
    }
}
