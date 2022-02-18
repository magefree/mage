package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KappaTechWrecker extends CardImpl {

    public KappaTechWrecker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.TURTLE);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Ninjutsu {1}{G}
        this.addAbility(new NinjutsuAbility("{1}{G}"));

        // Kappa Tech-Wrecker enters the battlefield with a deathtouch counter on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(
                CounterType.DEATHTOUCH.createInstance(1)
        ), "with a deathtouch counter on it"));

        // Whenever Kappa Tech-Wrecker deals combat damage to a player, you may remove a deathtouch counter from it. When you do, exile target artifact or enchantment that player controls.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(
                new KappaTechWreckerEffect(), false, true
        ));
    }

    private KappaTechWrecker(final KappaTechWrecker card) {
        super(card);
    }

    @Override
    public KappaTechWrecker copy() {
        return new KappaTechWrecker(this);
    }
}

class KappaTechWreckerEffect extends OneShotEffect {

    KappaTechWreckerEffect() {
        super(Outcome.Benefit);
        staticText = "you may remove a deathtouch counter from it. " +
                "When you do, exile target artifact or enchantment that player controls";
    }

    private KappaTechWreckerEffect(final KappaTechWreckerEffect effect) {
        super(effect);
    }

    @Override
    public KappaTechWreckerEffect copy() {
        return new KappaTechWreckerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        FilterPermanent filter = new FilterArtifactOrEnchantmentPermanent("artifact or enchantment that player controls");
        filter.add(new ControllerIdPredicate(getTargetPointer().getFirst(game, source)));
        ReflexiveTriggeredAbility ability = new ReflexiveTriggeredAbility(new ExileTargetEffect(), false);
        ability.addTarget(new TargetPermanent(filter));
        return new DoWhenCostPaid(
                ability, new RemoveCountersSourceCost(CounterType.DEATHTOUCH.createInstance()),
                "Remove a deathtouch counter?"
        ).apply(game, source);
    }
}
