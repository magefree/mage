
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.permanent.token.TokenImpl;

/**
 *
 * @author spjspj
 */
public final class Riddleform extends CardImpl {

    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public Riddleform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // Whenever you cast a noncreature spell, you may have Riddleform become a 3/3 Sphinx creature with flying in addition to its other types until end of turn.
        Effect effect = new BecomesCreatureSourceEffect(new RiddleformToken(), "", Duration.EndOfTurn);
        effect.setText("have {this} become a 3/3 Sphinx creature with flying in addition to its other types until end of turn.");
        this.addAbility(new SpellCastControllerTriggeredAbility(Zone.BATTLEFIELD, effect, filterNonCreature, true, true));

        // {2}{U}: Scry 1.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ScryEffect(1), new ManaCostsImpl<>("{2}{U}"));
        this.addAbility(ability);
    }

    private Riddleform(final Riddleform card) {
        super(card);
    }

    @Override
    public Riddleform copy() {
        return new Riddleform(this);
    }
}

class RiddleformToken extends TokenImpl {

    public RiddleformToken() {
        super("", "3/3 Sphinx creature with flying");
        cardType.add(CardType.CREATURE);

        subtype.add(SubType.SPHINX);
        power = new MageInt(3);
        toughness = new MageInt(3);
        addAbility(FlyingAbility.getInstance());
    }
    public RiddleformToken(final RiddleformToken token) {
        super(token);
    }

    public RiddleformToken copy() {
        return new RiddleformToken(this);
    }
}
