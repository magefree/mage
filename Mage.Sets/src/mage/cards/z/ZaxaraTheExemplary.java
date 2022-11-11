package mage.cards.z;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.AddManaOfAnyColorEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.Token;
import mage.game.permanent.token.ZaxaraTheExemplaryHydraToken;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 * @author AsterAether
 */
public final class ZaxaraTheExemplary extends CardImpl {

    final static String needPrefix = "_ZaxaraTheExemplary_NeedToken";

    public ZaxaraTheExemplary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.NIGHTMARE);
        this.subtype.add(SubType.HYDRA);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // {T}: Add two mana of any one color.
        Ability ability = new SimpleManaAbility(Zone.BATTLEFIELD, new AddManaOfAnyColorEffect(2), new TapSourceCost());
        this.addAbility(ability);

        // Whenever you cast a spell with {X} in its mana cost, create a 0/0 green Hydra creature token, then put X +1/+1 counters on it.
        this.addAbility(new ZaxaraTheExemplaryHydraTokenAbility());
    }

    private ZaxaraTheExemplary(final ZaxaraTheExemplary card) {
        super(card);
    }

    @Override
    public ZaxaraTheExemplary copy() {
        return new ZaxaraTheExemplary(this);
    }
}

class ZaxaraTheExemplaryHydraTokenAbility extends TriggeredAbilityImpl {

    public ZaxaraTheExemplaryHydraTokenAbility() {
        super(Zone.BATTLEFIELD, new ZaxaraTheExemplaryHydraTokenEffect(), false);
        setTriggerPhrase("Whenever you cast a spell with {X} in its mana cost");
    }

    public ZaxaraTheExemplaryHydraTokenAbility(final ZaxaraTheExemplaryHydraTokenAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.SPELL_CAST;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (!event.getPlayerId().equals(getControllerId()) || event.getType() != GameEvent.EventType.SPELL_CAST) {
            return false;
        }
        Spell spell = game.getStack().getSpell(event.getTargetId());
        if (spell == null || !spell.getSpellAbility().getManaCostsToPay().containsX()) {
            return false;
        }
        game.getState().setValue(this.getSourceId() + ZaxaraTheExemplary.needPrefix, spell);
        return true;
    }

    @Override
    public TriggeredAbility copy() {
        return new ZaxaraTheExemplaryHydraTokenAbility(this);
    }
}

class ZaxaraTheExemplaryHydraTokenEffect extends OneShotEffect {
    ZaxaraTheExemplaryHydraTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = ", create a 0/0 green Hydra creature token, then put X +1/+1 counters on it.";
    }

    ZaxaraTheExemplaryHydraTokenEffect(final ZaxaraTheExemplaryHydraTokenEffect effect) {
        super(effect);
    }

    @Override
    public ZaxaraTheExemplaryHydraTokenEffect copy() {
        return new ZaxaraTheExemplaryHydraTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player controller = game.getPlayer(source.getControllerId());
        if (player != null && controller != null) {
            Object needObject = game.getState().getValue(source.getSourceId() + ZaxaraTheExemplary.needPrefix);

            // create token
            if (needObject instanceof Spell) {
                Spell spell = (Spell) needObject;
                int xValue = spell.getSpellAbility().getManaCostsToPay().getX();

                Token hydraToken = new ZaxaraTheExemplaryHydraToken();
                hydraToken.putOntoBattlefield(1, game, source, source.getControllerId());

                for (UUID tokenId : hydraToken.getLastAddedTokenIds()) {
                    Permanent permanent = game.getPermanent(tokenId);
                    if (permanent != null)
                        permanent.addCounters(CounterType.P1P1.createInstance(xValue), source.getControllerId(), source, game);
                }
                return true;
            }
        }
        return false;
    }
}