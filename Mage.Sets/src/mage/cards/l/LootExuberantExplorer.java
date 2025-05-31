package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.abilities.effects.common.continuous.PlayAdditionalLandsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValueLessThanControlledLandCountPredicate;

import java.util.UUID;

/**
 *
 * @author ciaccona007
 */
public final class LootExuberantExplorer extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard(
            "creature card with mana value less than or equal to the number of lands you control"
    );

    static {
        filter.add(ManaValueLessThanControlledLandCountPredicate.instance);
    }

    public LootExuberantExplorer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // You may play an additional land on each of your turns.
        this.addAbility(new SimpleStaticAbility(
                new PlayAdditionalLandsControllerEffect(1, Duration.WhileOnBattlefield)));

        // {4}{G}{G}, {T}: Look at the top six cards of your library. You may reveal a creature card with mana value less than or equal to the number of lands you control from among them and put it onto the battlefield. Put the rest on the bottom in a random order.
        Ability ability = new SimpleActivatedAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter,
                PutCards.BATTLEFIELD, PutCards.BOTTOM_RANDOM
        ), new ManaCostsImpl<>("{4}{G}{G}"));
        ability.addCost(new TapSourceCost());
        this.addAbility(ability);
    }

    private LootExuberantExplorer(final LootExuberantExplorer card) {
        super(card);
    }

    @Override
    public LootExuberantExplorer copy() {
        return new LootExuberantExplorer(this);
    }
}
