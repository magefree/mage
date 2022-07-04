
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BecomesCreatureSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.TokenImpl;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class MythRealized extends CardImpl {
    
    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }
    
    public MythRealized(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}");

        // Whenever you cast a noncreature spell, put a lore counter on Myth Realized.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.LORE.createInstance()), filterNonCreature, false));

        // {2}{W}: Put a lore counter on Myth Realized.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.LORE.createInstance()), new ManaCostsImpl<>("{2}{W}")));

        // {W}: Until end of turn, Myth Realized becomes a Monk Avatar creature in addition to its other types and gains "This creature's power and toughness are each equal to the number of lore counters on it."
        Effect effect = new BecomesCreatureSourceEffect(new MythRealizedToken(), null, Duration.EndOfTurn);
        effect.setText("Until end of turn, {this} becomes a Monk Avatar creature in addition to its other types");
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{W}"));
        ability.addEffect(new MythRealizedSetPTEffect(Duration.EndOfTurn));
        this.addAbility(ability);        
        
    }

    private MythRealized(final MythRealized card) {
        super(card);
    }

    @Override
    public MythRealized copy() {
        return new MythRealized(this);
    }
}

class MythRealizedToken extends TokenImpl {

    public MythRealizedToken() {
        super("", "Monk Avatar creature in addition to its other types and gains \"This creature's power and toughness are each equal to the number of lore counters on it.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.MONK);
        subtype.add(SubType.AVATAR);
        power = new MageInt(0);
        toughness = new MageInt(0);
    }
    public MythRealizedToken(final MythRealizedToken token) {
        super(token);
    }

    @Override
    public MythRealizedToken copy() {
        return new MythRealizedToken(this);
    }
}

class MythRealizedSetPTEffect extends ContinuousEffectImpl {

    public MythRealizedSetPTEffect(Duration duration) {
        super(duration, Layer.PTChangingEffects_7, SubLayer.SetPT_7b, Outcome.BoostCreature);
        staticText = "and gains \"This creature's power and toughness are each equal to the number of lore counters on it.\"";
    }

    public MythRealizedSetPTEffect(final MythRealizedSetPTEffect effect) {
        super(effect);
    }

    @Override
    public MythRealizedSetPTEffect copy() {
        return new MythRealizedSetPTEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null && new MageObjectReference(source.getSourceObject(game), game).refersTo(permanent, game)) {
                int amount = permanent.getCounters(game).getCount(CounterType.LORE);
                permanent.getPower().setValue(amount);
                permanent.getToughness().setValue(amount);
                return true;
            } else {
                discard();
            }
        }
        return false;
    }

}
