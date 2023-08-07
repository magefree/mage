package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.OfferingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlastFurnaceHellkite extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledArtifactPermanent("artifact");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("creatures attacking your opponents");

    static {
        filter2.add(BlastFurnaceHellkitePredicate.instance);
    }

    public BlastFurnaceHellkite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{7}{R}{R}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Artifact offering
        this.addAbility(new OfferingAbility(filter));

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // Creatures attacking your opponents have double strike.
        this.addAbility(new SimpleStaticAbility(new GainAbilityAllEffect(
                DoubleStrikeAbility.getInstance(), Duration.WhileOnBattlefield, filter2
        )));
    }

    private BlastFurnaceHellkite(final BlastFurnaceHellkite card) {
        super(card);
    }

    @Override
    public BlastFurnaceHellkite copy() {
        return new BlastFurnaceHellkite(this);
    }
}

enum BlastFurnaceHellkitePredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        return game.getOpponents(input.getSource().getControllerId())
                .contains(game.getCombat().getDefenderId(input.getObject().getId()));
    }
}