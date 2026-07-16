package mage.cards.f;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class FuturistForge extends CardImpl {

    public FuturistForge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{U}");

        // When this artifact enters, draw a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawCardSourceControllerEffect(1)));

        // {3}{U}, Sacrifice this artifact: Draw two cards.
        Ability ability = new SimpleActivatedAbility(
            new DrawCardSourceControllerEffect(2), new ManaCostsImpl<>("{3}{U}")
        );
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private FuturistForge(final FuturistForge card) {
        super(card);
    }

    @Override
    public FuturistForge copy() {
        return new FuturistForge(this);
    }
}
