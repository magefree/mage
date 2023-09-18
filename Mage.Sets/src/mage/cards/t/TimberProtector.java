package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class TimberProtector extends CardImpl {

    private static final FilterCreaturePermanent filterTreefolk
            = new FilterCreaturePermanent("Treefolk creatures");
    private static final FilterControlledPermanent filterBoth
            = new FilterControlledPermanent("other Treefolk and Forests you control");

    static {
        filterTreefolk.add(SubType.TREEFOLK.getPredicate());
        filterBoth.add(TimberProtectorPredicate.instance);
    }

    public TimberProtector(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Other Treefolk creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filterTreefolk, true
        )));

        // Other Treefolk and Forests you control are indestructible.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                IndestructibleAbility.getInstance(), Duration.WhileOnBattlefield, filterBoth
        )));
    }

    private TimberProtector(final TimberProtector card) {
        super(card);
    }

    @Override
    public TimberProtector copy() {
        return new TimberProtector(this);
    }
}

enum TimberProtectorPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        MageObject obj = input.getObject();
        if (obj.getId().equals(input.getSourceId())) {
            return obj.hasSubtype(SubType.FOREST, game);
        }
        return obj.hasSubtype(SubType.FOREST, game)
                || obj.hasSubtype(SubType.TREEFOLK, game);
    }
}
