package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.ThopterColorlessToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThopterShop extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(CardType.ARTIFACT.getPredicate());
    }

    public ThopterShop(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");

        // Whenever one or more artifact creatures you control die, draw a card. This ability triggers only once each turn.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ).setTriggersOnce(true).setTriggerPhrase("Whenever one or more artifact creatures you control die, "));

        // {2}{W}, {T}: Create a 1/1 colorless Thopter artifact creature token with flying.
        Ability ability = new SimpleActivatedAbility(
                new CreateTokenEffect(new ThopterColorlessToken()), new ManaCostsImpl<>("{2}{W}")
        );
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private ThopterShop(final ThopterShop card) {
        super(card);
    }

    @Override
    public ThopterShop copy() {
        return new ThopterShop(this);
    }
}
