package mage.cards.l;

import java.util.UUID;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.effects.common.counter.AddCountersAttachedEffect;
import mage.constants.Outcome;
import mage.target.TargetPermanent;
import mage.abilities.Ability;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class LevelUp extends CardImpl {

    public LevelUp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When this Aura enters, put a +1/+1 counter on enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new AddCountersAttachedEffect(CounterType.P1P1.createInstance(), "enchanted creature"), false));

        // Enchanted creature has "Whenever this creature attacks, double the number of +1/+1 counters on it. Then if it has power 10 or greater, draw a card."
        this.addAbility(new SimpleStaticAbility(
            new GainAbilityAttachedEffect(
                new AttacksTriggeredAbility(new LevelUpEffect(), false),
                AttachmentType.AURA
            )
        ));
    }

    private LevelUp(final LevelUp card) {
        super(card);
    }

    @Override
    public LevelUp copy() {
        return new LevelUp(this);
    }
}

class LevelUpEffect extends OneShotEffect {

    public LevelUpEffect() {
        super(Outcome.Benefit);
        staticText = "double the number of +1/+1 counters on it. Then if it has power 10 or greater, draw a card";
    }

    private LevelUpEffect(final LevelUpEffect effect) {
        super(effect);
    }

    @Override
    public LevelUpEffect copy() {
        return new LevelUpEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent != null) {
            int counters = permanent.getCounters(game).getCount(CounterType.P1P1);
            permanent.addCounters(CounterType.P1P1.createInstance(counters), source, game);
            if (permanent.getPower().getValue() >= 10) {
                new DrawCardSourceControllerEffect(1).apply(game, source);
            }
            return true;
        }
        return false;
    }
}
