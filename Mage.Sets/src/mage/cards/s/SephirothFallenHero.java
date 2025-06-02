package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.ModifiedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SephirothFallenHero extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("a modified creature");

    static {
        filter.add(ModifiedPredicate.instance);
    }

    public SephirothFallenHero(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Jenova Cells -- Whenever Sephiroth attacks, you may put a cell counter on target creature. Until end of turn, each modified creature you control has base power and toughness 7/5.
        Ability ability = new AttacksTriggeredAbility(new SephirothFallenHeroEffect());
        ability.addEffect(new SetBasePowerToughnessAllEffect(
                7, 5, Duration.EndOfTurn, filter
        ).setText("until end of turn, each modified creature you control has base power and toughness 7/5"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability.withFlavorWord("Jenova Cells"));

        // The Reunion -- {3}, Sacrifice a modified creature: Return this card from your graveyard to the battlefield tapped.
        ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true), new GenericManaCost(3)
        );
        ability.addCost(new SacrificeTargetCost(filter));
        this.addAbility(ability.withFlavorWord("The Reunion"));
    }

    private SephirothFallenHero(final SephirothFallenHero card) {
        super(card);
    }

    @Override
    public SephirothFallenHero copy() {
        return new SephirothFallenHero(this);
    }
}

class SephirothFallenHeroEffect extends OneShotEffect {

    SephirothFallenHeroEffect() {
        super(Outcome.Benefit);
        staticText = "you may put a cell counter on target creature";
    }

    private SephirothFallenHeroEffect(final SephirothFallenHeroEffect effect) {
        super(effect);
    }

    @Override
    public SephirothFallenHeroEffect copy() {
        return new SephirothFallenHeroEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        return player != null && permanent != null
                && player.chooseUse(outcome, "Put a cell counter on " + permanent.getIdName() + "?", source, game)
                && permanent.addCounters(CounterType.CELL.createInstance(), source, game);
    }
}
