package mage.cards.g;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.conditional.ConditionalSpellManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author woshikie
 */
public final class GiadaFontOfHope extends CardImpl {

    public static final FilterPermanent OTHER_ANGEL_YOU_CONTROL_FILTER = new FilterControlledCreaturePermanent(SubType.ANGEL, "other Angel you control");

    static {
        OTHER_ANGEL_YOU_CONTROL_FILTER.add(AnotherPredicate.instance);
    }

    public GiadaFontOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Each other Angel you control enters the battlefield with an additional +1/+1 counter on it for each Angel you already control.
        this.addAbility(
                new EntersBattlefieldControlledTriggeredAbility(
                        Zone.BATTLEFIELD,
                        new GiadaFontOfHopeEffect(),
                        OTHER_ANGEL_YOU_CONTROL_FILTER,
                        false,
                        SetTargetPointer.PERMANENT,
                        "Each other Angel you control enters the battlefield with an additional +1/+1 counter on it for each Angel you already control."
                )
        );

        // {T}: Add {W}. Spend this mana only to cast an Angel spell.
        this.addAbility(
                new ConditionalColoredManaAbility(
                        Mana.WhiteMana(1),
                        new ConditionalSpellManaBuilder(
                                new FilterCreatureSpell(SubType.ANGEL)
                        )
                )
        );
    }

    private GiadaFontOfHope(final GiadaFontOfHope card) {
        super(card);
    }

    @Override
    public GiadaFontOfHope copy() {
        return new GiadaFontOfHope(this);
    }
}

class GiadaFontOfHopeEffect extends OneShotEffect {

    GiadaFontOfHopeEffect() {
        super(Outcome.Benefit);
    }

    private GiadaFontOfHopeEffect(final GiadaFontOfHopeEffect effect) {
        super(effect);
    }

    @Override
    public Effect copy() {
        return new GiadaFontOfHopeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }

        int counters =
                (int) game.getBattlefield().getAllPermanents().stream().filter(perm -> {
                    return perm.hasSubtype(SubType.ANGEL, game) // perm is Angel
                            && perm.isControlledBy(source.getControllerId()) // perm is Controlled by player
                            && perm.getId() != permanent.getId(); // don't count self
                }).count();
        return permanent.addCounters(CounterType.P1P1.createInstance(counters), source.getControllerId(), source, game);
    }
}