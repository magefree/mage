package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.token.SaprolingToken;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public class NemataPrimevalWarden extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Saproling");

    static {
        filter.add(SubType.SAPROLING.getPredicate());
    }

    public NemataPrimevalWarden(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // If a creature an opponent controls would die, exile it instead. When you do, create a 1/1 green Saproling creature token.
        this.addAbility(new SimpleStaticAbility(new NemataPrimevalWardenEffect()));

        // {G}, Sacrifice a Saproling: Nemata, Primeval Warden gets +2/+2 until end of turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{G}"));
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, filter, false)));
        this.addAbility(ability);

        // {1}{B}, Sacrifice 2 Saprolings: Draw a card.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new ManaCostsImpl<>("{1}{B}"));
        ability2.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(2, 2, filter, false)));
        this.addAbility(ability2);
    }

    private NemataPrimevalWarden(final NemataPrimevalWarden card) {
        super(card);
    }

    @Override
    public NemataPrimevalWarden copy() {
        return new NemataPrimevalWarden(this);
    }
}

class NemataPrimevalWardenEffect extends ReplacementEffectImpl {

    NemataPrimevalWardenEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Exile);
        staticText = "If a creature an opponent controls would die, exile it instead. " +
                "When you do, create a 1/1 green Saproling creature token.";
    }

    private NemataPrimevalWardenEffect(final NemataPrimevalWardenEffect effect) {
        super(effect);
    }

    @Override
    public NemataPrimevalWardenEffect copy() {
        return new NemataPrimevalWardenEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        ((ZoneChangeEvent) event).setToZone(Zone.EXILED);
        game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken()), false,
                "If a creature an opponent controls would die, exile it instead. " +
                        "When you do, create a 1/1 green Saproling creature token"
        ), source);
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        return zEvent.isDiesEvent()
                && zEvent.getTarget() != null
                && zEvent.getTarget().isCreature(game)
                && game.getOpponents(zEvent.getTarget().getControllerId()).contains(source.getControllerId());
    }
}
