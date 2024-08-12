package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldCastTriggeredAbility;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.*;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.mageobject.BasePowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jimga150
 */
public final class RapidAugmenter extends CardImpl {

    private static final FilterPermanent filterBP1 = new FilterControlledCreaturePermanent("another creature you control with base power 1");

    static {
        filterBP1.add(AnotherPredicate.instance);
        filterBP1.add(new BasePowerPredicate(ComparisonType.EQUAL_TO, 1));
    }

    private static final FilterPermanent filterAnother = new FilterControlledCreaturePermanent("another creature you control");

    static {
        filterAnother.add(AnotherPredicate.instance);
    }

    public RapidAugmenter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");
        
        this.subtype.add(SubType.OTTER);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever another creature you control with base power 1 enters, it gains haste until end of turn.
        Ability ability = new EntersBattlefieldControlledTriggeredAbility(
                Zone.BATTLEFIELD, new RapidAugmenterHasteEffect(), filterBP1, false);
        this.addAbility(ability);

        // Whenever another creature you control enters, if it wasn't cast, put a +1/+1 counter on Rapid Augmenter
        // and Rapid Augmenter can't be blocked this turn.
        Ability ability2 = new EntersBattlefieldCastTriggeredAbility(Zone.BATTLEFIELD, new AddCountersSourceEffect(
                CounterType.P1P1.createInstance()), filterAnother,
                false, SetTargetPointer.PERMANENT, false);
        ability2.addEffect(new CantBeBlockedSourceEffect(Duration.EndOfTurn).concatBy(" and "));
        this.addAbility(ability2);
    }

    private RapidAugmenter(final RapidAugmenter card) {
        super(card);
    }

    @Override
    public RapidAugmenter copy() {
        return new RapidAugmenter(this);
    }
}

// Based on GainAbilitySourceEffect
class RapidAugmenterHasteEffect extends ContinuousEffectImpl {

    protected Ability ability = HasteAbility.getInstance();

    public RapidAugmenterHasteEffect() {
        super(Duration.EndOfTurn, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.staticText = "it gains haste until end of turn";
        this.generateGainAbilityDependencies(ability, null);
    }

    protected RapidAugmenterHasteEffect(final RapidAugmenterHasteEffect effect) {
        super(effect);
        ability.newId(); // This is needed if the effect is copied e.g. by a clone so the ability can be added multiple times to permanents
    }

    @Override
    public RapidAugmenterHasteEffect copy() {
        return new RapidAugmenterHasteEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
        // If source permanent is no longer onto battlefield discard the effect
        if (source.getSourcePermanentIfItStillExists(game) == null) {
            discard();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = (Permanent) getValue("permanentEnteringBattlefield");
        if (permanent != null) {
            permanent.addAbility(ability, source.getSourceId(), game);
            return true;
        }
        return true;
    }
}
