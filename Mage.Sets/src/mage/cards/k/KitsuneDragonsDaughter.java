package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.continuous.ExchangeControlTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.meta.OrTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KitsuneDragonsDaughter extends CardImpl {

    public KitsuneDragonsDaughter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.FOX);
        this.subtype.add(SubType.WARLOCK);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Kitsune enters or deals combat damage to a player, you may exchange control of two other target creatures controlled by different players.
        Ability ability = new OrTriggeredAbility(
                Zone.BATTLEFIELD,
                new ExchangeControlTargetEffect(
                        Duration.Custom, "exchange control of two other " +
                        "target creatures controlled by different players"
                ), true, "Whenever {this} enters or deals combat damage to a player, ",
                new EntersBattlefieldTriggeredAbility(null),
                new DealsCombatDamageToAPlayerTriggeredAbility(null)
        );
        ability.addTarget(new KitsuneDragonsDaughterTarget());
        this.addAbility(ability);
    }

    private KitsuneDragonsDaughter(final KitsuneDragonsDaughter card) {
        super(card);
    }

    @Override
    public KitsuneDragonsDaughter copy() {
        return new KitsuneDragonsDaughter(this);
    }
}

class KitsuneDragonsDaughterTarget extends TargetPermanent {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("other creatures controlled by different players");

    static {
        filter.add(AnotherPredicate.instance);
    }

    KitsuneDragonsDaughterTarget() {
        super(2, 2, filter, false);
    }

    private KitsuneDragonsDaughterTarget(final KitsuneDragonsDaughterTarget target) {
        super(target);
    }

    @Override
    public KitsuneDragonsDaughterTarget copy() {
        return new KitsuneDragonsDaughterTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Game game) {
        if (!super.canTarget(playerId, id, source, game)) {
            return false;
        }
        Permanent creature = game.getPermanent(id);
        if (creature == null) {
            return false;
        }
        return this.getTargets()
                .stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(permanent -> !creature.getId().equals(permanent.getId())
                        && creature.isControlledBy(permanent.getControllerId())
                );
    }
}
