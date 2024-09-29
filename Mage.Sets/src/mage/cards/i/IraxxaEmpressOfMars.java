package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.BattleCryAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.card.CastFromZonePredicate;
import mage.game.permanent.token.AlienWarriorToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class IraxxaEmpressOfMars extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell from anywhere other than your hand");

    static {
        filter.add(Predicates.not(new CastFromZonePredicate(Zone.HAND)));
    }

    public IraxxaEmpressOfMars(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ALIEN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Battle cry
        this.addAbility(new BattleCryAbility());

        // Paradox -- Whenever you cast a spell from anywhere other than your hand, create a 2/2 red Alien Warrior creature token.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CreateTokenEffect(new AlienWarriorToken()),
                filter, false
        ).setAbilityWord(AbilityWord.PARADOX));
    }

    private IraxxaEmpressOfMars(final IraxxaEmpressOfMars card) {
        super(card);
    }

    @Override
    public IraxxaEmpressOfMars copy() {
        return new IraxxaEmpressOfMars(this);
    }
}
