package mage.cards.k;

import mage.MageObject;
import mage.abilities.common.AttachableToRestrictedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author LevelX
 */
public final class KondasBanner extends CardImpl {

    private static final FilterPermanent legendaryFilter = new FilterCreaturePermanent("legendary creature");
    private static final FilterPermanent colorFilter = new FilterCreaturePermanent("Creatures that share a color with equipped creature");
    private static final FilterPermanent typeFilter = new FilterCreaturePermanent("Creatures that share a creature type with equipped creature");

    static {
        legendaryFilter.add(SuperType.LEGENDARY.getPredicate());
        colorFilter.add(ShareColorEquippedPredicate.instance);
        typeFilter.add(ShareTypeEquippedPredicate.instance);
    }

    public KondasBanner(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Konda's Banner can be attached only to a legendary creature.
        this.addAbility(new AttachableToRestrictedAbility(new TargetPermanent(legendaryFilter)));

        // Creatures that share a color with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, colorFilter)));

        // Creatures that share a creature type with equipped creature get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, typeFilter)));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.AddAbility, new GenericManaCost(2), new TargetPermanent(legendaryFilter.copy().add(TargetController.YOU.getControllerPredicate())), false));
    }

    private KondasBanner(final KondasBanner card) {
        super(card);
    }

    @Override
    public KondasBanner copy() {
        return new KondasBanner(this);
    }
}

enum ShareColorEquippedPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        Permanent source = game.getPermanent(input.getSourceId());
        if (source == null) {
            return false;
        }
        Permanent attached = game.getPermanent(source.getAttachedTo());
        if (attached == null) {
            return false;
        }
        return input.getObject().getColor(game).shares(attached.getColor(game));
    }
}

enum ShareTypeEquippedPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        Permanent source = game.getPermanent(input.getSourceId());
        if (source == null) {
            return false;
        }
        Permanent attached = game.getPermanent(source.getAttachedTo());
        if (attached == null) {
            return false;
        }
        return input.getObject().shareCreatureTypes(game, attached);
    }
}
