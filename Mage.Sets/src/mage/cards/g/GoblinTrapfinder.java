package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.SeekCardEffect;
import mage.abilities.effects.common.cost.SpellCostReductionSourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.util.PerpetualUtil;

import java.util.UUID;

public final class GoblinTrapfinder extends CardImpl {

    public GoblinTrapfinder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.addAbility(new DiesSourceTriggeredAbility(new GoblinTrapfinderEffect()));
    }

    private GoblinTrapfinder(final GoblinTrapfinder card) {
        super(card);
    }

    @Override
    public GoblinTrapfinder copy() {
        return new GoblinTrapfinder(this);
    }
}

class GoblinTrapfinderEffect extends OneShotEffect {

    GoblinTrapfinderEffect() {
        super(Outcome.Benefit);
        staticText = "seek a creature card with mana value 3 or less. That card perpetually gains haste, \"This spell costs {1} less to cast,\" and \"At the beginning of your end step, sacrifice this creature.\"";
    }

    private GoblinTrapfinderEffect(final GoblinTrapfinderEffect effect) {
        super(effect);
    }

    @Override
    public GoblinTrapfinderEffect copy() {
        return new GoblinTrapfinderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        FilterCard filter = new FilterCreatureCard();
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
        Card card = SeekCardEffect.seekCard(player, filter, source, game);
        if (card == null) {
            return false;
        }
        PerpetualUtil.addAbility(card, HasteAbility.getInstance(), source, game);
        PerpetualUtil.addAbility(card, new SimpleStaticAbility(Zone.ALL,
                new SpellCostReductionSourceEffect(1)), source, game);
        PerpetualUtil.addAbility(card, new BeginningOfEndStepTriggeredAbility(
                TargetController.YOU, new SacrificeSourceEffect(), false), source, game);
        return true;
    }
}
