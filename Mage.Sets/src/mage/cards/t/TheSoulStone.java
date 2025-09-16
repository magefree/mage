package mage.cards.t;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.SourceHarnessedCondition;
import mage.abilities.costs.common.ExileTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class TheSoulStone extends CardImpl {

    public TheSoulStone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.INFINITY);
        this.subtype.add(SubType.STONE);

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());

        // {6}{B}, {T}, Exile a creature you control: Harness The Soul Stone.
        Ability ability = new SimpleActivatedAbility(new HarnessSoulStoneEffect(), new ManaCostsImpl<>("{6}{B}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileTargetCost(new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_A_CREATURE)));
        this.addAbility(ability);

        // ∞ -- At the beginning of your upkeep, return target creature card from your graveyard to the battlefield.
        Ability soulStoneAbility = new BeginningOfUpkeepTriggeredAbility(new ReturnFromGraveyardToBattlefieldTargetEffect())
                .withFlavorWord("∞");
        soulStoneAbility.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(soulStoneAbility),
                SourceHarnessedCondition.instance,
                "")
                .setText(""))
        );
    }

    private TheSoulStone(final TheSoulStone card) {
        super(card);
    }

    @Override
    public TheSoulStone copy() {
        return new TheSoulStone(this);
    }
}

class HarnessSoulStoneEffect extends OneShotEffect {

    public HarnessSoulStoneEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "Harness {this}. <i>(Once harnessed, its ∞ ability is active.)<i>";
    }

    protected HarnessSoulStoneEffect(final HarnessSoulStoneEffect effect) {
        super(effect);
    }

    @Override
    public HarnessSoulStoneEffect copy() {
        return new HarnessSoulStoneEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        permanent.setHarnessed(game, true);
        return true;
    }
}
