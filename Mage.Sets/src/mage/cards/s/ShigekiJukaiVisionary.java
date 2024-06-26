package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandFromBattlefieldSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.RevealLibraryPickControllerEffect;
import mage.abilities.keyword.ChannelAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.XTargetsCountAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShigekiJukaiVisionary extends CardImpl {
    private static final FilterCard filter = new FilterCard("nonlegendary cards from your graveyard");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    public ShigekiJukaiVisionary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // {1}{G}, {T}, Return Shigeki, Jukai Visionary to its owner's hand: Reveal the top four cards of your library.
        // You may put a land card from among them onto the battlefield tapped. Put the rest into your graveyard.
        Ability ability = new SimpleActivatedAbility(
                new RevealLibraryPickControllerEffect(
                        4, 1,
                        StaticFilters.FILTER_CARD_LAND_A,
                        PutCards.BATTLEFIELD_TAPPED,
                        PutCards.GRAVEYARD),
                new ManaCostsImpl<>("{1}{G}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ReturnToHandFromBattlefieldSourceCost());
        this.addAbility(ability);

        // Channel — {X}{X}{G}{G}, Discard Shigeki: Return X target nonlegendary cards from your graveyard to your hand.
        Ability ability2 = new ChannelAbility(
                "{X}{X}{G}{G}", new ReturnFromGraveyardToHandTargetEffect()
                .setText("return X target nonlegendary cards from your graveyard to your hand")
        );
        ability2.addTarget(new TargetCardInYourGraveyard(filter));
        ability2.setTargetAdjuster(new XTargetsCountAdjuster());
        this.addAbility(ability2);
    }

    private ShigekiJukaiVisionary(final ShigekiJukaiVisionary card) {
        super(card);
    }

    @Override
    public ShigekiJukaiVisionary copy() {
        return new ShigekiJukaiVisionary(this);
    }
}
