package mage.cards.z;

import mage.MageInt;
import mage.MageItem;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.PutSourceCountersOnTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.PermanentReferenceInCollectionPredicate;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.RandomUtil;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author TheElk801
 */
public final class ZackFair extends CardImpl {

    public ZackFair(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // Zack Fair enters with a +1/+1 counter on it.
        this.addAbility(new EntersBattlefieldAbility(
                new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                "with a +1/+1 counter on it"
        ));

        // {1}, Sacrifice Zack Fair: Target creature you control gains indestructible until end of turn. Put Zack Fair's counters on that creature and attach an Equipment that was attached to Zack Fair to that creature.
        Ability ability = new SimpleActivatedAbility(
                new GainAbilityTargetEffect(IndestructibleAbility.getInstance()), new GenericManaCost(1)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new PutSourceCountersOnTargetEffect().setText("Put {this}'s counters on that creature"));
        ability.addEffect(new ZackFairEffect());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ZackFair(final ZackFair card) {
        super(card);
    }

    @Override
    public ZackFair copy() {
        return new ZackFair(this);
    }
}

class ZackFairEffect extends OneShotEffect {

    ZackFairEffect() {
        super(Outcome.Benefit);
        staticText = "and attach an Equipment that was attached to {this} to that creature";
    }

    private ZackFairEffect(final ZackFairEffect effect) {
        super(effect);
    }

    @Override
    public ZackFairEffect copy() {
        return new ZackFairEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentOrLKI(game);
        Permanent creature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null || creature == null) {
            return false;
        }
        List<Permanent> permanents = permanent
                .getAttachments()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .filter(p -> p.hasSubtype(SubType.EQUIPMENT, game))
                .collect(Collectors.toList());
        Permanent equipment;
        switch (permanents.size()) {
            case 0:
                return false;
            case 1:
                equipment = RandomUtil.randomFromCollection(permanents);
                break;
            default:
                FilterPermanent filter = new FilterPermanent(
                        SubType.EQUIPMENT, "Equipment to attach to " + creature.getIdName()
                );
                filter.add(new PermanentReferenceInCollectionPredicate(permanents, game));
                TargetPermanent target = new TargetPermanent(filter);
                target.withNotTarget(true);
                Optional.ofNullable(source)
                        .map(Controllable::getControllerId)
                        .map(game::getPlayer)
                        .ifPresent(player -> player.choose(outcome, target, source, game));
                equipment = game.getPermanent(target.getFirstTarget());
        }
        return Optional
                .ofNullable(equipment)
                .map(MageItem::getId)
                .map(uuid -> creature.addAttachment(uuid, source, game))
                .orElse(false);
    }
}
