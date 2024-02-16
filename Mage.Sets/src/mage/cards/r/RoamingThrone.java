package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.AddChosenSubtypeEffect;
import mage.abilities.effects.common.enterAttribute.EnterAttributeAddChosenSubtypeEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.NumberOfTriggersEvent;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class RoamingThrone extends CardImpl {

    public RoamingThrone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{4}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // As Roaming Throne enters the battlefield, choose a creature type.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseCreatureTypeEffect(Outcome.Benefit));

        // Roaming Throne is the chosen type in addition to its other types.
        ability.addEffect(new EnterAttributeAddChosenSubtypeEffect());
        this.addAbility(ability);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AddChosenSubtypeEffect()));

        // If a triggered ability of another creature you control of the chosen type triggers, it triggers an additional time.
        this.addAbility(new SimpleStaticAbility(new RoamingThroneReplacementEffect()));
    }

    private RoamingThrone(final RoamingThrone card) {
        super(card);
    }

    @Override
    public RoamingThrone copy() {
        return new RoamingThrone(this);
    }
}

class RoamingThroneReplacementEffect extends ReplacementEffectImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    RoamingThroneReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a triggered ability of another creature you control of the chosen type triggers, "
                + "it triggers an additional time";
    }

    private RoamingThroneReplacementEffect(final RoamingThroneReplacementEffect effect) {
        super(effect);
    }

    @Override
    public RoamingThroneReplacementEffect copy() {
        return new RoamingThroneReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.NUMBER_OF_TRIGGERS;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!(event instanceof NumberOfTriggersEvent)) {
            return false;
        }
        NumberOfTriggersEvent numberOfTriggersEvent = (NumberOfTriggersEvent) event;
        if (!source.isControlledBy(event.getPlayerId())) {
            return false;
        }
        Permanent permanentSource = game.getPermanentOrLKIBattlefield(numberOfTriggersEvent.getSourceId());
        return permanentSource != null
                && filter.match(permanentSource, source.getControllerId(), source, game)
                && permanentSource.hasSubtype(ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(event.getAmount() + 1);
        return false;
    }
}
