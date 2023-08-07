package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.abilityword.ConstellationAbility;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledEnchantmentPermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.EnchantedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CalixGuidedByFate extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("{this} or an enchanted creature you control");

    static {
        filter.add(CalixGuidedByFatePredicate.instance);
    }

    public CalixGuidedByFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{1}{G}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Constellation--Whenever Calix, Guided by Fate or another enchantment enters the battlefield under your control, put a +1/+1 counter on target creature.
        Ability ability = new ConstellationAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever Calix or an enchanted creature you control deals combat damage to a player, you may create a token that's a copy of a nonlegendary enchantment you control. Do this only once each turn.
        this.addAbility(new DealsDamageToAPlayerAllTriggeredAbility(
                new CalixGuidedByFateEffect(), filter, true, SetTargetPointer.NONE, true
        ).setDoOnlyOnceEachTurn(true));
    }

    private CalixGuidedByFate(final CalixGuidedByFate card) {
        super(card);
    }

    @Override
    public CalixGuidedByFate copy() {
        return new CalixGuidedByFate(this);
    }
}

enum CalixGuidedByFatePredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        if (!AnotherPredicate.instance.apply(input, game)) {
            return true;
        }
        return input.getSource().isControlledBy(game.getControllerId(input.getObject().getId()))
                && input.getObject() instanceof Permanent
                && EnchantedPredicate.instance.apply((Permanent) input.getObject(), game);
    }
}

class CalixGuidedByFateEffect extends OneShotEffect {

    private static final FilterPermanent filter
            = new FilterControlledEnchantmentPermanent("nonlegendary enchantment you control");

    static {
        filter.add(Predicates.not(SuperType.LEGENDARY.getPredicate()));
    }

    CalixGuidedByFateEffect() {
        super(Outcome.Benefit);
        staticText = "create a token that's a copy of a nonlegendary enchantment you control";
    }

    private CalixGuidedByFateEffect(final CalixGuidedByFateEffect effect) {
        super(effect);
    }

    @Override
    public CalixGuidedByFateEffect copy() {
        return new CalixGuidedByFateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        Permanent permanent = game.getPermanent(target.getFirstTarget());
        return permanent != null && new CreateTokenCopyTargetEffect().setSavedPermanent(permanent).apply(game, source);
    }
}
