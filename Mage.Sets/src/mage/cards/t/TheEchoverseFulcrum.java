package mage.cards.t;

import java.util.UUID;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class TheEchoverseFulcrum extends CardImpl {

    public TheEchoverseFulcrum(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);

        // When The Echoverse Fulcrum enters, draw a card, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(1, 1)));

        // {5}, {T}, Exile The Echoverse Fulcrum: Destroy all creatures. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            new DestroyAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES),
            new ManaCostsImpl<>("{5}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileSourceCost());
        this.addAbility(ability);
    }

    private TheEchoverseFulcrum(final TheEchoverseFulcrum card) {
        super(card);
    }

    @Override
    public TheEchoverseFulcrum copy() {
        return new TheEchoverseFulcrum(this);
    }
}
