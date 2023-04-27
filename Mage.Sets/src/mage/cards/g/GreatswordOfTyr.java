package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GreatswordOfTyr extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(GreatswordOfTyrPredicate.instance);
    }

    public GreatswordOfTyr(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        this.subtype.add(SubType.EQUIPMENT);

        // Whenever equipped creature attacks, put a +1/+1 counter on it and tap up to one target creature defending player controls.
        Ability ability = new AttacksAttachedTriggeredAbility(
                new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "it")
        );
        ability.addEffect(new TapTargetEffect().concatBy("and"));
        ability.addTarget(new TargetPermanent(0, 1, filter));
        this.addAbility(ability);

        // Equip {W}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new ManaCostsImpl<>("{W}")));
    }

    private GreatswordOfTyr(final GreatswordOfTyr card) {
        super(card);
    }

    @Override
    public GreatswordOfTyr copy() {
        return new GreatswordOfTyr(this);
    }
}

enum GreatswordOfTyrPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return Optional.ofNullable(input.getSource().getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(uuid -> game.getCombat().getDefendingPlayerId(uuid, game))
                .map(input.getObject()::isControlledBy)
                .orElse(false);
    }

    @Override
    public String toString() {
        return "";
    }
}
