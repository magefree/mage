package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.search.SearchLibraryPutInHandEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.permanent.token.BeastToken;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HerdMigration extends CardImpl {

    public HerdMigration(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{6}{G}");

        // Domain — Create a 3/3 green Beast creature token for each basic land type among lands you control.
        this.getSpellAbility().addEffect(new CreateTokenEffect(new BeastToken(), DomainValue.REGULAR));
        this.getSpellAbility().setAbilityWord(AbilityWord.DOMAIN);
        this.getSpellAbility().addHint(DomainHint.instance);

        // {1}{G}, Discard Herd Migration: Search your library for a basic land card, reveal it, put it into your hand, then shuffle. You gain 3 life.
        Ability ability = new SimpleActivatedAbility(
                Zone.HAND,
                new SearchLibraryPutInHandEffect(
                        new TargetCardInLibrary(StaticFilters.FILTER_CARD_BASIC_LAND_A), true
                ), new ManaCostsImpl<>("{1}{G}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addEffect(new GainLifeEffect(3));
        this.addAbility(ability);
    }

    private HerdMigration(final HerdMigration card) {
        super(card);
    }

    @Override
    public HerdMigration copy() {
        return new HerdMigration(this);
    }
}
