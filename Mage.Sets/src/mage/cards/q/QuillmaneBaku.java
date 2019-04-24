
package mage.cards.q;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.RemoveVariableCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author LevelX2
 */
public final class QuillmaneBaku extends CardImpl {

    private final UUID originalId;

    public QuillmaneBaku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever you cast a Spirit or Arcane spell, you may put a ki counter on Skullmane Baku.
        this.addAbility(new SpellCastControllerTriggeredAbility(new AddCountersSourceEffect(CounterType.KI.createInstance()), StaticFilters.SPIRIT_OR_ARCANE_CARD, true));

        //TODO: Make ability properly copiable
        // {1}, Tap, Remove X ki counters from Quillmane Baku: Return target creature with converted mana cost X or less to its owner's hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new QuillmaneBakuReturnEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new RemoveVariableCountersSourceCost(CounterType.KI.createInstance(1)));
        ability.addTarget(new TargetCreaturePermanent());
        originalId = ability.getOriginalId();
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability.getOriginalId().equals(originalId)) {
            int maxConvManaCost = 0;
            for (Cost cost : ability.getCosts()) {
                if (cost instanceof RemoveVariableCountersSourceCost) {
                    maxConvManaCost = ((RemoveVariableCountersSourceCost) cost).getAmount();
                }
            }
            ability.getTargets().clear();
            FilterCreaturePermanent newFilter = new FilterCreaturePermanent("creature with converted mana cost " + maxConvManaCost + " or less");
            newFilter.add(new ConvertedManaCostPredicate(ComparisonType.FEWER_THAN, maxConvManaCost + 1));
            TargetCreaturePermanent target = new TargetCreaturePermanent(newFilter);
            ability.getTargets().add(target);
        }
    }

    public QuillmaneBaku(final QuillmaneBaku card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public QuillmaneBaku copy() {
        return new QuillmaneBaku(this);
    }

    static class QuillmaneBakuReturnEffect extends OneShotEffect {

        public QuillmaneBakuReturnEffect() {
            super(Outcome.ReturnToHand);
            this.staticText = "Return target creature with converted mana cost X or less to its owner's hand";
        }

        public QuillmaneBakuReturnEffect(final QuillmaneBakuReturnEffect effect) {
            super(effect);
        }

        @Override
        public QuillmaneBakuReturnEffect copy() {
            return new QuillmaneBakuReturnEffect(this);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller == null) {
                return false;
            }
            Permanent permanent = game.getPermanent(this.getTargetPointer().getFirst(game, source));
            if (permanent != null) {
                controller.moveCards(permanent, Zone.HAND, source, game);
            }
            return true;
        }
    }
}
