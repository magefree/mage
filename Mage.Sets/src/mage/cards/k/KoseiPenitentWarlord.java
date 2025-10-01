package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToOpponentTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DamageEachOtherOpponentThatMuchEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.Counter;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KoseiPenitentWarlord extends CardImpl {

    public KoseiPenitentWarlord(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // As long as Kosei, Penitent Warlord is enchanted, equipped, and has a counter on it, Kosei has "Whenever Kosei, Penitent Warlord deals combat damage to an opponent, you draw that many cards and Kosei deals that much damage to each other opponent."
        Ability ability = new DealsDamageToOpponentTriggeredAbility(
                new DrawCardSourceControllerEffect(SavedDamageValue.MANY),
                false, true, true
        );
        ability.addEffect(new DamageEachOtherOpponentThatMuchEffect()
                .setText("and {this} deals that much damage to each other opponent"));
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(ability), KoseiPenitentWarlordCondition.instance, "as long as " +
                "{this} is enchanted, equipped, and has a counter on it, {this} has \"Whenever {this} deals combat " +
                "damage to an opponent, you draw that many cards and {this} deals that much damage to each other opponent.\""
        )));
    }

    private KoseiPenitentWarlord(final KoseiPenitentWarlord card) {
        super(card);
    }

    @Override
    public KoseiPenitentWarlord copy() {
        return new KoseiPenitentWarlord(this);
    }
}

enum KoseiPenitentWarlordCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        return sourcePermanent != null
                && sourcePermanent.getCounters(game)
                .values()
                .stream()
                .mapToInt(Counter::getCount)
                .anyMatch(x -> x > 0)
                && sourcePermanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.EQUIPMENT, game))
                && sourcePermanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .anyMatch(permanent -> permanent.hasSubtype(SubType.AURA, game));
    }
}
