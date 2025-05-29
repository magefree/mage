package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.token.SpiritXXToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbzanMonument extends CardImpl {

    private static final FilterCard filter = new FilterCard("a basic Plains, Swamp, or Forest card");

    static {
        filter.add(SuperType.BASIC.getPredicate());
        filter.add(Predicates.or(
                SubType.PLAINS.getPredicate(),
                SubType.SWAMP.getPredicate(),
                SubType.FOREST.getPredicate()
        ));
    }

    public AbzanMonument(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // When this artifact enters, search your library for a basic Plains, Swamp, or Forest card, reveal it, put it into your hand, then shuffle.
        this.addAbility(new EntersBattlefieldTriggeredAbility(
                new SearchLibraryPutInHandEffect(new TargetCardInLibrary(filter), true)
        ));

        // {1}{W}{B}{G}, {T}, Sacrifice this artifact: Create an X/X white Spirit creature token, where X is the greatest toughness among creatures you control. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AbzanMonumentEffect(), new ManaCostsImpl<>("{1}{W}{B}{G}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability.addHint(GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.getHint()));
    }

    private AbzanMonument(final AbzanMonument card) {
        super(card);
    }

    @Override
    public AbzanMonument copy() {
        return new AbzanMonument(this);
    }
}

class AbzanMonumentEffect extends OneShotEffect {

    AbzanMonumentEffect() {
        super(Outcome.Benefit);
        staticText = "create an X/X white Spirit creature token, " +
                "where X is the greatest toughness among creatures you control";
    }

    private AbzanMonumentEffect(final AbzanMonumentEffect effect) {
        super(effect);
    }

    @Override
    public AbzanMonumentEffect copy() {
        return new AbzanMonumentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int value = GreatestAmongPermanentsValue.TOUGHNESS_CONTROLLED_CREATURES.calculate(game, source, this);
        return new CreateTokenEffect(new SpiritXXToken(value)).apply(game, source);
    }
}
