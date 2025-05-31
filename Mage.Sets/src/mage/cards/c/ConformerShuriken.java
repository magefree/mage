package mage.cards.c;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.constants.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.DefendingPlayerControlsSourceAttackingPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author balazskristof
 */
public final class ConformerShuriken extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creature defending player controls");

    static {
        filter.add(DefendingPlayerControlsSourceAttackingPredicate.instance);
    }

    public ConformerShuriken(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature has "Whenever this creature attacks, tap target creature defending player controls. If that creature has greater power than this creature, put a number of +1/+1 counters on this creature equal to the difference."
        Ability ability = new AttacksTriggeredAbility(new ConformerShurikenEffect())
                .setTriggerPhrase("Whenever this creature attacks, ");
        ability.addTarget(new TargetCreaturePermanent(filter));
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
        super(Outcome.BoostCreature);
        staticText = "tap target creature defending player controls. If that creature has greater power than this creature, "
                + "put a number of +1/+1 counters on this creature equal to the difference.";
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
        Permanent target = game.getPermanent(source.getFirstTarget());
        if (target != null) {
            target.tap(source, game);
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                int targetPower = target.getPower().getValue();
                int permanentPower = permanent.getPower().getValue();
                if (targetPower > permanentPower) {
                    permanent.addCounters(CounterType.P1P1.createInstance(targetPower - permanentPower), source, game);
                }
            }
        }
        return true;
    }
}