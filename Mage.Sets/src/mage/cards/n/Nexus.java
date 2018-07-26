package mage.cards.n;

import java.util.UUID;

import mage.Mana;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.CardsInHandCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.TapSourceEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Zone;

/**
 *
 * @author NinthWorld
 */
public final class Nexus extends CardImpl {

    public Nexus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");
        

        // Nexus enters the battlefield tapped unless you have five or more cards in hand.
        Condition controls = new InvertCondition(new CardsInHandCondition(ComparisonType.MORE_THAN, 4));
        String abilityText = " tapped unless you have five or more cards in hand";
        this.addAbility(new EntersBattlefieldAbility(new ConditionalOneShotEffect(new TapSourceEffect(), controls, abilityText), abilityText));

        // {T}: Add {W} or {U} to your mana pool.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.WhiteMana(1), new TapSourceCost()));
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.BlueMana(1), new TapSourceCost()));
    }

    public Nexus(final Nexus card) {
        super(card);
    }

    @Override
    public Nexus copy() {
        return new Nexus(this);
    }
}
