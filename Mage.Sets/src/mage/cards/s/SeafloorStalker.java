package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.PartyCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.abilities.hint.common.PartyCountHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SeafloorStalker extends CardImpl {

    public SeafloorStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // {4}{U}: Seafloor Stalker gets +1/+0 until end of turn and can't be blocked this turn. This ability costs {1} less to activate for each creature in your party.
        // TODO: Make ability properly copiable
        Ability ability = new SimpleActivatedAbility(
                new BoostSourceEffect(1, 0, Duration.EndOfTurn), new ManaCostsImpl<>("{4}{U}")
        );
        ability.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).setText("and can't be blocked this turn"));
        ability.addEffect(new InfoEffect(
                "This ability costs {1} less to activate for each creature in your party. " + PartyCount.getReminder()
        ));
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SeafloorStalkerCostIncreasingEffect(ability.getOriginalId())
        ).addHint(PartyCountHint.instance));
    }

    private SeafloorStalker(final SeafloorStalker card) {
        super(card);
    }

    @Override
    public SeafloorStalker copy() {
        return new SeafloorStalker(this);
    }
}

class SeafloorStalkerCostIncreasingEffect extends CostModificationEffectImpl {

    private final UUID originalId;

    SeafloorStalkerCostIncreasingEffect(UUID originalId) {
        super(Duration.EndOfGame, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.originalId = originalId;
    }

    private SeafloorStalkerCostIncreasingEffect(final SeafloorStalkerCostIncreasingEffect effect) {
        super(effect);
        this.originalId = effect.originalId;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int count = PartyCount.instance.calculate(game, source, this);
            CardUtil.reduceCost(abilityToModify, count);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.getOriginalId().equals(originalId);
    }

    @Override
    public SeafloorStalkerCostIncreasingEffect copy() {
        return new SeafloorStalkerCostIncreasingEffect(this);
    }
}
