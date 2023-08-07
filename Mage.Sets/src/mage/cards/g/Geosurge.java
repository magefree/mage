
package mage.cards.g;

import mage.ConditionalMana;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.game.Game;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class Geosurge extends CardImpl {

    public Geosurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{R}{R}{R}{R}");


        this.getSpellAbility().addEffect(new BasicManaEffect(new GeosurgeConditionalMana()));
    }

    private Geosurge(final Geosurge card) {
        super(card);
    }

    @Override
    public Geosurge copy() {
        return new Geosurge(this);
    }
}

class GeosurgeConditionalMana extends ConditionalMana {

    public GeosurgeConditionalMana() {
        super(Mana.RedMana(7));
        staticText = "Spend this mana only to cast artifact or creature spells";
        addCondition(new GeosurgeManaCondition());
    }

    private GeosurgeConditionalMana(final GeosurgeConditionalMana conditionalMana) {
        super(conditionalMana);
    }

    @Override
    public GeosurgeConditionalMana copy() {
        return new GeosurgeConditionalMana(this);
    }
}

class GeosurgeManaCondition implements Condition {

    @Override
    public boolean apply(Game game, Ability source) {
        if (source instanceof SpellAbility) {
            Card card = game.getCard(source.getSourceId());
            if (card != null && (card.isArtifact(game) || card.isCreature(game))) {
                return true;
            }
        }
        return false;
    }
}
