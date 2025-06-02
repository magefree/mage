package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.GetXValue;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldWithCounterTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetadjustment.ManaValueTargetAdjuster;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RydiaSummonerOfMist extends CardImpl {

    private static final FilterCard filter = new FilterCard("Saga card with mana value X from your graveyard");

    static {
        filter.add(SubType.SAGA.getPredicate());
    }

    public RydiaSummonerOfMist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Landfall -- Whenever a land you control enters, you may discard a card. If you do, draw a card.
        this.addAbility(new LandfallAbility(new DoIfCostPaid(
                new DrawCardSourceControllerEffect(1), new DiscardCardCost()
        )));

        // Summon -- {X}, {T}: Return target Saga card with mana value X from your graveyard to the battlefield with a finality counter on it. It gains haste until end of turn. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new RydiaSummonerOfMistEffect(), new ManaCostsImpl<>("{X}"));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        ability.setTargetAdjuster(new ManaValueTargetAdjuster(GetXValue.instance, ComparisonType.EQUAL_TO));
        this.addAbility(ability.withFlavorWord("Summon"));
    }

    private RydiaSummonerOfMist(final RydiaSummonerOfMist card) {
        super(card);
    }

    @Override
    public RydiaSummonerOfMist copy() {
        return new RydiaSummonerOfMist(this);
    }
}

class RydiaSummonerOfMistEffect extends ReturnFromGraveyardToBattlefieldWithCounterTargetEffect {

    RydiaSummonerOfMistEffect() {
        super(CounterType.FINALITY.createInstance());
    }

    private RydiaSummonerOfMistEffect(final RydiaSummonerOfMistEffect effect) {
        super(effect);
    }

    @Override
    public RydiaSummonerOfMistEffect copy() {
        return new RydiaSummonerOfMistEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        super.apply(game, source);
        Optional.ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getCard)
                .map(card -> CardUtil.getPermanentFromCardPutToBattlefield(card, game))
                .ifPresent(permanent -> game.addEffect(
                        new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn)
                                .setTargetPointer(new FixedTarget(permanent, game)), source));
        return true;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode) + ". It gains haste until end of turn";
    }
}
