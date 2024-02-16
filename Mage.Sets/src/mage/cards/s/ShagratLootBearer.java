package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.AmassEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShagratLootBearer extends CardImpl {

    public ShagratLootBearer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Shagrat, Loot Bearer attacks, attach up to one target Equipment to it. Then amass Orcs X, where X is the number of Equipment attached to Shagrat.
        Ability ability = new AttacksTriggeredAbility(new ShagratLootBearerEffect());
        ability.addTarget(new TargetPermanent(0, 1, StaticFilters.FILTER_PERMANENT_EQUIPMENT));
        this.addAbility(ability);
    }

    private ShagratLootBearer(final ShagratLootBearer card) {
        super(card);
    }

    @Override
    public ShagratLootBearer copy() {
        return new ShagratLootBearer(this);
    }
}

class ShagratLootBearerEffect extends OneShotEffect {

    ShagratLootBearerEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment to it. Then amass Orcs X, " +
                "where X is the number of Equipment attached to {this}";
    }

    private ShagratLootBearerEffect(final ShagratLootBearerEffect effect) {
        super(effect);
    }

    @Override
    public ShagratLootBearerEffect copy() {
        return new ShagratLootBearerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.addAttachment(getTargetPointer().getFirst(game, source), source, game);
        }
        int count = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .filter(Objects::nonNull)
                .map(Permanent::getAttachments)
                .map(Collection::stream)
                .map(s -> s
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .filter(p -> p.hasSubtype(SubType.EQUIPMENT, game))
                        .mapToInt(x -> 1)
                        .sum())
                .orElse(0);
        AmassEffect.doAmass(count, SubType.ORC, game, source);
        return true;
    }
}
