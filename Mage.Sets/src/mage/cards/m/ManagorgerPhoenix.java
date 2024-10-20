package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.MageObjectReference;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.common.SourceInGraveyardCondition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlTargetEffect;
import mage.abilities.effects.common.continuous.BoostTargetPerpetuallyEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.Card;
import mage.constants.*;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterSpell;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author karapuzz14
 */
public final class ManagorgerPhoenix extends CardImpl {

    public ManagorgerPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{R}");
        
        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Managorger Phoenix can't block.
        this.addAbility(new CantBlockAbility());

        // Whenever you cast a spell, if Managorger Phoenix is in your graveyard, put a flame counter on Managorger Phoenix for each {R} in that spell's mana cost. If Managorger Phoenix has five or more flame counters on it, return it to the battlefield and it perpetually gets +1/+1.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new SpellCastControllerTriggeredAbility(Zone.GRAVEYARD, new ManagorgerPhoenixEffect(), new FilterSpell(), false, SetTargetPointer.SPELL),
                SourceInGraveyardCondition.instance, "Whenever you cast a spell, if Managorger Phoenix is in your graveyard," +
                " put a flame counter on Managorger Phoenix for each {R} in that spell's mana cost. " +
                "If Managorger Phoenix has five or more flame counters on it, " +
                "return it to the battlefield and it perpetually gets +1/+1."
        ));
    }

    private ManagorgerPhoenix(final ManagorgerPhoenix card) {
        super(card);
    }

    @Override
    public ManagorgerPhoenix copy() {
        return new ManagorgerPhoenix(this);
    }
}
class ManagorgerPhoenixEffect extends OneShotEffect {

    ManagorgerPhoenixEffect() {
        super(Outcome.Benefit);
    }

    private ManagorgerPhoenixEffect(final ManagorgerPhoenixEffect effect) {
        super(effect);
    }

    @Override
    public ManagorgerPhoenixEffect copy() {
        return new ManagorgerPhoenixEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Spell spell = (Spell) this.getValue("spellCast");
        if(controller != null && spell != null) {

            int redManaCount = spell.getManaCost().getMana().getRed();
            AddCountersSourceEffect effect = new AddCountersSourceEffect(CounterType.FLAME.createInstance(), StaticValue.get(redManaCount), true, true);
            effect.apply(game, source);

            Card card = game.getCard(source.getSourceId());
            if (card.getCounters(game).getCount("flame") >= 5) {
                ReturnToBattlefieldUnderOwnerControlTargetEffect returnEffect = new ReturnToBattlefieldUnderOwnerControlTargetEffect(false, false);
                returnEffect.setTargetPointer(new FixedTarget(card.getId()));
                returnEffect.apply(game, source);

                game.addEffect(new BoostTargetPerpetuallyEffect(1, 0).setTargetPointer(new FixedTarget(card.getId())), source);
            }
            return true;
        }
        return false;
    }
}