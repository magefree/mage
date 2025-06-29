package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ConformerShuriken extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public ConformerShuriken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "Whenever this creature attacks, tap target creature defending player controls. If that creature has greater power than this creature, put a number of +1/+1 counters on this creature equal to the difference."
        Ability ability = new AttacksTriggeredAbility(new TapTargetEffect());
        ability.addEffect(new ConformerShurikenEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(new SimpleStaticAbility(new GainAbilityAttachedEffect(ability, AttachmentType.EQUIPMENT)));

        // Equip {2}
        this.addAbility(new EquipAbility(2));
    }

    private ConformerShuriken(final ConformerShuriken card) {
        super(card);
    }

    @Override
    public ConformerShuriken copy() {
        return new ConformerShuriken(this);
    }
}

class ConformerShurikenEffect extends OneShotEffect {

    ConformerShurikenEffect() {
        super(Outcome.Benefit);
        staticText = "If that creature has greater power than this creature, " +
                "put a number of +1/+1 counters on this creature equal to the difference";
    }

    private ConformerShurikenEffect(final ConformerShurikenEffect effect) {
        super(effect);
    }

    @Override
    public ConformerShurikenEffect copy() {
        return new ConformerShurikenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null
                && Optional
                .ofNullable(getTargetPointer().getFirst(game, source))
                .map(game::getPermanent)
                .map(MageObject::getPower)
                .map(MageInt::getValue)
                .map(targetsPower -> targetsPower - permanent.getPower().getValue())
                .filter(x -> x > 0 && permanent.addCounters(CounterType.P1P1.createInstance(x), source, game))
                .isPresent();
    }
}
