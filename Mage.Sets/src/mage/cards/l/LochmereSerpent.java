package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetCardInOpponentsGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LochmereSerpent extends CardImpl {

    private static final FilterControlledPermanent filter1
            = new FilterControlledPermanent(SubType.ISLAND, "an Island");
    private static final FilterControlledPermanent filter2
            = new FilterControlledPermanent(SubType.SWAMP, "a Swamp");

    public LochmereSerpent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{B}");

        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // {U}, Sacrifice an Island: Lochmere Serpent can't be blocked this turn.
        Ability ability = new SimpleActivatedAbility(
                new CantBeBlockedSourceEffect(Duration.EndOfTurn), new ManaCostsImpl<>("{U}")
        );
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter1)));
        this.addAbility(ability);

        // {B}, Sacrifice a Swamp: You gain 1 life and draw a card.
        ability = new SimpleActivatedAbility(new GainLifeEffect(1), new ManaCostsImpl<>("{B}"));
        ability.addEffect(new DrawCardSourceControllerEffect(1).concatBy("and"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter2)));
        this.addAbility(ability);

        // {U}{B}: Exile five target cards from an opponent's graveyard. Return Lochmere Serpent from your graveyard to your hand. Activate this ability only any time you could cast a sorcery.
        ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD,
                new ExileTargetEffect().setText("Exile five target cards from an opponent's graveyard."),
                new ManaCostsImpl<>("{U}{B}")
        );
        ability.addEffect(new ReturnSourceFromGraveyardToHandEffect());
        ability.addTarget(new TargetCardInOpponentsGraveyard(
                5, 5, StaticFilters.FILTER_CARD, true
        ));
        this.addAbility(ability);
    }

    private LochmereSerpent(final LochmereSerpent card) {
        super(card);
    }

    @Override
    public LochmereSerpent copy() {
        return new LochmereSerpent(this);
    }
}
