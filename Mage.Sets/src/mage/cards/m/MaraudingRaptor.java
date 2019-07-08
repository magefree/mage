package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MaraudingRaptor extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature spells");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("another creature");

    static {
        filter2.add(AnotherPredicate.instance);
    }

    public MaraudingRaptor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.DINOSAUR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Creature spells you cast cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)));

        // Whenever another creature enters the battlefield under your control, Marauding Raptor deals 2 damage to it. If a Dinosaur is dealt damage this way, Marauding Raptor gets +2/+0 until end of turn.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new MaraudingRaptorEffect(),
                filter2, false, SetTargetPointer.PERMANENT,
                "Whenever another creature enters the battlefield under your control, " +
                        "{this} deals 2 damage to it. If a Dinosaur is dealt damage this way, " +
                        "{this} gets +2/+0 until end of turn."
        ));
    }

    private MaraudingRaptor(final MaraudingRaptor card) {
        super(card);
    }

    @Override
    public MaraudingRaptor copy() {
        return new MaraudingRaptor(this);
    }
}

class MaraudingRaptorEffect extends OneShotEffect {

    MaraudingRaptorEffect() {
        super(Outcome.Benefit);
    }

    private MaraudingRaptorEffect(final MaraudingRaptorEffect effect) {
        super(effect);
    }

    @Override
    public MaraudingRaptorEffect copy() {
        return new MaraudingRaptorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        if (permanent.damage(2, source.getSourceId(), game) > 0 && permanent.hasSubtype(SubType.DINOSAUR, game)) {
            game.addEffect(new BoostSourceEffect(2, 0, Duration.EndOfTurn), source);
        }
        return true;
    }
}