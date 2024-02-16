

package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LevelX2
 */
public final class Guttersnipe extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("an instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                CardType.INSTANT.getPredicate(),
                CardType.SORCERY.getPredicate()));
    }

    public Guttersnipe (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever you cast an instant or sorcery spell, Guttersnipe deals 2 damage to each opponent.
        this.addAbility(new SpellCastControllerTriggeredAbility(new DamagePlayersEffect(2, TargetController.OPPONENT), filter, false));
    }

    private Guttersnipe(final Guttersnipe card) {
        super(card);
    }

    @Override
    public Guttersnipe copy() {
        return new Guttersnipe(this);
    }
}
