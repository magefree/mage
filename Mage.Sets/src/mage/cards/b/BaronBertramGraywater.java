package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldOneOrMoreTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.permanent.token.VampireRogueToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class BaronBertramGraywater extends CardImpl {

    static final FilterPermanent filter = new FilterPermanent("tokens");

    static {
        filter.add(TokenPredicate.TRUE);
    }

    public BaronBertramGraywater(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever one or more tokens enter the battlefield under your control, create a 1/1 black Vampire Rogue creature token with lifelink. This ability triggers only once each turn.
        this.addAbility(new EntersBattlefieldOneOrMoreTriggeredAbility(
                new CreateTokenEffect(new VampireRogueToken()), filter, TargetController.YOU
        ).setTriggersLimitEachTurn(1));

        // {1}{B}, Sacrifice another creature or artifact: Draw a card.
        Ability ability = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new ManaCostsImpl<>("{1}{B}"));
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE_OR_ARTIFACT));
        this.addAbility(ability);
    }

    private BaronBertramGraywater(final BaronBertramGraywater card) {
        super(card);
    }

    @Override
    public BaronBertramGraywater copy() {
        return new BaronBertramGraywater(this);
    }
}
