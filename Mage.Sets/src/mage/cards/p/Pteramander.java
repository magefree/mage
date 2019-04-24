package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.effects.keyword.AdaptEffect;
import mage.abilities.hint.ValueHint;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Pteramander extends CardImpl {

    static final DynamicValue cardsCount = new CardsInControllerGraveyardCount(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY);

    public Pteramander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}");

        this.subtype.add(SubType.SALAMANDER);
        this.subtype.add(SubType.DRAKE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {7}{U}: Adapt 4. This ability costs {1} less to activate for each instant and sorcery card in your graveyard.
        // TODO: Make ability properly copiable
        Ability ability = new SimpleActivatedAbility(new AdaptEffect(4).setText("Adapt 4. This ability costs {1} less to activate for each instant and sorcery card in your graveyard."), new ManaCostsImpl("{7}{U}"));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new PteramanderCostIncreasingEffect(ability.getOriginalId()))
                .addHint(new ValueHint("Instant and sorcery cards in your graveyard", cardsCount)));
    }

    private Pteramander(final Pteramander card) {
        super(card);
    }

    @Override
    public Pteramander copy() {
        return new Pteramander(this);
    }
}

class PteramanderCostIncreasingEffect extends CostModificationEffectImpl {

    private final UUID originalId;

    PteramanderCostIncreasingEffect(UUID originalId) {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.originalId = originalId;
    }

    private PteramanderCostIncreasingEffect(final PteramanderCostIncreasingEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = Pteramander.cardsCount.calculate(game, source, this);
            CardUtil.reduceCost(abilityToModify, count);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public PteramanderCostIncreasingEffect copy() {
        return new PteramanderCostIncreasingEffect(this);
    }

}
