package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author Loki
 */
public final class DeathBaron extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Skeletons you control and other Zombies");

    static {
        filter.add(DeathBaronPredicate.instance);
    }

    public DeathBaron(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Skeletons you control and other Zombies you control get +1/+1 and have deathtouch.
        Ability ability = new SimpleStaticAbility(new BoostControlledEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                DeathtouchAbility.getInstance(), Duration.WhileOnBattlefield, filter
        ).setText("and have deathtouch"));
        this.addAbility(ability);
    }

    private DeathBaron(final DeathBaron card) {
        super(card);
    }

    @Override
    public DeathBaron copy() {
        return new DeathBaron(this);
    }
}

enum DeathBaronPredicate implements ObjectSourcePlayerPredicate<MageObject> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<MageObject> input, Game game) {
        if (input.getObject().hasSubtype(SubType.SKELETON, game)) {
            return true;
        }
        return !input.getObject().getId().equals(input.getSourceId())
                && input.getObject().hasSubtype(SubType.ZOMBIE, game);
    }
}
